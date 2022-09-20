package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.auth.data.repository.user.StudentRepository
import com.info.info_v1_backend.domain.auth.exception.UserNotFoundException
import com.info.info_v1_backend.domain.project.business.controller.dto.request.EditIndividualProjectDto
import com.info.info_v1_backend.domain.project.business.controller.dto.request.IndividualProjectCreateRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.request.IndividualProjectEditRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.controller.dto.response.MinimumProjectResponse
import com.info.info_v1_backend.domain.project.data.entity.Creation
import com.info.info_v1_backend.domain.project.data.entity.project.IndividualProject
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import com.info.info_v1_backend.domain.project.data.repository.CreationRepository
import com.info.info_v1_backend.domain.project.data.repository.ProjectRepository
import com.info.info_v1_backend.domain.project.exception.NotHaveAccessProjectException
import com.info.info_v1_backend.domain.project.exception.ProjectNotFoundException
import com.info.info_v1_backend.global.error.common.InternalServerErrorException
import com.info.info_v1_backend.global.util.user.CurrentUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class IndividualProjectServiceImpl(
    private val individualRepository: ProjectRepository<IndividualProject>,
    private val currentUtil: CurrentUtil,
    private val userRepository: StudentRepository,
    private val creationRepository: CreationRepository
    ): IndividualProjectService{

    override fun getMinimumLatestOrderProjectList(idx: Int, size: Int): Page<MinimumProjectResponse>{
        //security config에서 url로 authentication필요
        return individualRepository.findAll(
            PageRequest.of(
                idx,
                size,
                Sort.by(Sort.Direction.DESC, "createAt")))
            .map{
                MinimumProjectResponse(
                    projectId = it.id
                        ?: throw InternalServerErrorException("$it :: null일 수 없는 프로젝트 아이디"),
                    name = it.name,
                    haveSeenCount = it.haveSeenCount,
                    createAt = it.createdDate,
                    updateAt = it.updateDate,
                    createdBy = it.createdBy,
                    updatedBy = it.updatedBy,
                    shortContent = it.shortContent,
                    githubLinkList = it.codeLinkList,
                    imageLinkList = it.imageLinkList?.map {it1 ->
                        it1.toImageDto()
                    }?.toMutableList()
                )
            }
    }

    override fun getMinimumNumberOfViewsProjectList(idx: Int, size: Int): Page<MinimumProjectResponse>{
        //security config에서 url로 authentication필요
        return individualRepository.findAll(
            PageRequest.of(
                idx,
                size,
                Sort.by(Sort.Direction.DESC, "haveSeenCount")))
            .map{
                MinimumProjectResponse(
                    projectId = it.id
                        ?: throw InternalServerErrorException("$it :: null일 수 없는 프로젝트 아이디"),
                    name = it.name,
                    haveSeenCount = it.haveSeenCount,
                    createAt = it.createdDate,
                    updateAt = it.updateDate,
                    createdBy = it.createdBy,
                    updatedBy = it.updatedBy,
                    shortContent = it.shortContent,
                    githubLinkList = it.codeLinkList,
                    imageLinkList = it.imageLinkList?.map {it1 ->
                        it1.toImageDto()
                    }?.toMutableList()
                )
            }
    }

    override fun getMaximumProject(id: Long): MaximumProjectResponse {
        //security config에서 url로 authentication필요
        val p = individualRepository.findByIdOrNull(id)
            ?: throw ProjectNotFoundException("$id :: not found")
        p.editIndividualProject(
            EditIndividualProjectDto(
                id = null,
                name = null,
                shortContent = null,
                haveSeenCount = p.haveSeenCount + 1,
                creationList = null,
                codeLinkList = null,
                tagList = null,
                status = null
            ))
        val s = p.creationList
            ?.map { it.student.id }
            ?.toMutableList()
        return MaximumProjectResponse(
            name = p.name,
            imageLink = p.imageLinkList?.map {
                it.toImageDto()
            }?.toMutableList(),
            createBy = p.createdBy,
            updateBy = p.updatedBy,
            createAt = p.createdDate,
            updateAt = p.updateDate,
            projectStatus = ProjectStatus.INDIVIDUAL,
            shortContent = p.shortContent,
            haveSeenCount = p.haveSeenCount,
            githubLinkList = p.codeLinkList,
            studentIdList = s ?: throw InternalServerErrorException("${p.id} :: 올바르지 않은 프로젝트 입니다")
        )
    }

    override fun writeIndividualProject(request: IndividualProjectCreateRequest) {
        verifyUser(request.studentIdList)
        val c = request.studentIdList.stream()
            .map { creationRepository.save(Creation(
                project = null,
                student = userRepository.findByIdOrNull(it)
                    ?: throw UserNotFoundException("$it :: not found")
            ))}
            .collect(Collectors.toList())

        val p = individualRepository.save(IndividualProject(
            name = request.name,
            shortContent = request.shortContent,
            codeLinkList = request.githubLinkList,
            tagList = request.tagList,
            creationList = c
        ))

        c.map {
            it.id?.let {it1 -> creationRepository.findByIdOrNull(it1)
                    ?.editCreation(project = p)
            }
        }
    }

    override fun editIndividualProject(request: IndividualProjectEditRequest) {
        verifyUser(request.studentIdList)
        val p = individualRepository.findByIdOrNull(request.projectId)
            ?: throw ProjectNotFoundException("${request.projectId} :: not found")
        p.creationList
            ?.map { it.id?.let { it1 -> creationRepository.deleteById(it1) } }
        val c = request.studentIdList
            .map { creationRepository.save(
                Creation(
                    project = p,
                    student = userRepository.findById(it).orElse(null)
                ) ) }.toList()
        p.editIndividualProject(
            EditIndividualProjectDto(
                id = p.id,
                name = request.name,
                shortContent = request.shortContent,
                haveSeenCount = p.haveSeenCount,
                creationList = c,
                codeLinkList = request.githubLinkList,
                tagList = request.tagList,
                status = null
            )
        )
    }

    private fun verifyUser(studentIdList: List<Long>) {
        if(studentIdList.stream()
                .anyMatch { currentUtil.getCurrentUser() == userRepository.findById(it) }){
            throw NotHaveAccessProjectException("작성자가 프로젝트에 참여하지 않음")
        }
    }

}