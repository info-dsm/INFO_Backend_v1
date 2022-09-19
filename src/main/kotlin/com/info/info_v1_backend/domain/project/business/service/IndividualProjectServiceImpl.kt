package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.auth.data.repository.user.StudentRepository
import com.info.info_v1_backend.domain.auth.exception.UserNotFoundException
import com.info.info_v1_backend.domain.project.business.controller.dto.request.EditIndividualProjectDto
import com.info.info_v1_backend.domain.project.business.controller.dto.request.IndividualProjectCreateRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.request.IndividualProjectEditRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.controller.dto.response.MinimumProjectListResponse
import com.info.info_v1_backend.domain.project.business.controller.dto.response.MinimumProjectResponse
import com.info.info_v1_backend.domain.project.data.entity.Creation
import com.info.info_v1_backend.domain.project.data.entity.project.IndividualProject
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import com.info.info_v1_backend.domain.project.data.repository.CreationRepository
import com.info.info_v1_backend.domain.project.data.repository.IndividualProjectRepository
import com.info.info_v1_backend.domain.project.exception.NotHaveAccessProjectException
import com.info.info_v1_backend.domain.project.exception.ProjectNotFoundException
import com.info.info_v1_backend.global.util.user.CurrentUtil
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

    override fun getMinimumLatestOrderProjectList   (): MinimumProjectListResponse {
        //security config에서 url로 authentication필요
        return MinimumProjectListResponse(individualRepository.findAll(
            Sort.by(Sort.Direction.DESC, "createdAt"))
            .stream()
            .filter { it.status == ProjectStatus.APPROVE }
            .map{
                MinimumProjectResponse(
                    it.id!!,
                    it.name,
                    it.createdAt,
                    it.updatedAt,
                    it.createdBy,
                    it.updatedBy,
                    it.shortContent,
                    it.haveSeenCount,
                    it.codeLinkList,
                )
            }.collect(Collectors.toList()))
    }

    override fun getMinimumNumberOfViewsProjectList(): MinimumProjectListResponse {
        //security config에서 url로 authentication필요
        return MinimumProjectListResponse(individualRepository.findAll(
            Sort.by(Sort.Direction.DESC, "haveSeenCount"))
            .stream()
            .filter { it.status == ProjectStatus.APPROVE }
            .map{
                MinimumProjectResponse(
                    it.id!!,
                    it.name,
                    it.createdAt,
                    it.updatedAt,
                    it.createdBy,
                    it.updatedBy,
                    it.shortContent,
                    it.haveSeenCount,
                    it.codeLinkList,
                )
            }.collect(Collectors.toList()))
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
        return MaximumProjectResponse(
            p.photoList.map { 
                it.toImageDto()
            },
            p.name,
            p.createdAt,
            p.updatedAt,
            p.createdBy,
            p.updatedBy,
            p.status,
            p.shortContent,
            p.haveSeenCount,
            p.codeLinkList
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
            .map { it.id?.let { it1 -> creationRepository.deleteById(it1) } }
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