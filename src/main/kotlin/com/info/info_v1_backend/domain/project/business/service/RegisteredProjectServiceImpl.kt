package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.auth.data.repository.user.StudentRepository
import com.info.info_v1_backend.domain.auth.exception.UserNotFoundException
import com.info.info_v1_backend.domain.project.business.controller.dto.request.EditRegisteredProjectDto
import com.info.info_v1_backend.domain.project.business.controller.dto.request.RegisteredProjectCreateRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.request.RegisteredProjectEditRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.controller.dto.response.MinimumProjectListResponse
import com.info.info_v1_backend.domain.project.business.controller.dto.response.MinimumProjectResponse
import com.info.info_v1_backend.domain.project.data.entity.Creation
import com.info.info_v1_backend.domain.project.data.entity.project.RegisteredProject
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import com.info.info_v1_backend.domain.project.data.repository.CreationRepository
import com.info.info_v1_backend.domain.project.data.repository.ProjectRepository
import com.info.info_v1_backend.domain.project.exception.NotHaveAccessProjectException
import com.info.info_v1_backend.domain.project.exception.ProjectNotFoundException
import com.info.info_v1_backend.domain.project.exception.ProjectStatusWaitingException
import com.info.info_v1_backend.global.util.user.CurrentUtil
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class RegisteredProjectServiceImpl(
    private val registeredProjectRepository: ProjectRepository<RegisteredProject>,
    private val currentUtil: CurrentUtil,
    private val userRepository: StudentRepository,
    private val creationRepository: CreationRepository
    ): RegisteredProjectService {

    override fun getMinimumLatestOrderProjectList(): MinimumProjectListResponse {
        //security config에서 url로 authentication필요
        return MinimumProjectListResponse(registeredProjectRepository.findAll(Sort
            .by(Sort.Direction.DESC, "createdAt"))
            .stream()
            .filter { it.status == ProjectStatus.APPROVE }
            .map{
                MinimumProjectResponse(
                name = it.name,
                haveSeenCount = it.haveSeenCount,
                createAt = it.createdDate,
                updateAt = it.updateDate,
                createdBy = it.createdBy,
                updatedBy = it.updatedBy,
                shortContent = it.shortContent,
                githubLinkList = it.codeLinkList
            )
            }.collect(Collectors.toList()))
    }

    override fun getMinimumNumberOfViewsProjectList(): MinimumProjectListResponse {
        //security config에서 url로 authentication필요
        return MinimumProjectListResponse(registeredProjectRepository.findAll(Sort
            .by(Sort.Direction.DESC, "haveSeenCount"))
            .stream()
            .filter { it.status != ProjectStatus.APPROVE }
            .map{
                MinimumProjectResponse(
                name = it.name,
                haveSeenCount = it.haveSeenCount,
                createAt = it.createdDate,
                updateAt = it.updateDate,
                createdBy = it.createdBy,
                updatedBy = it.updatedBy,
                shortContent = it.shortContent,
                githubLinkList = it.codeLinkList
            )
            }.collect(Collectors.toList()))
    }

    override fun getMaximumProject(id: Long): MaximumProjectResponse {
        //security config에서 url로 authentication필요
        val p = registeredProjectRepository.findByIdOrNull(id)
            ?: throw ProjectNotFoundException("$id :: not found")
        if (p.status == ProjectStatus.WAITING) {
            throw ProjectStatusWaitingException("$id :: status waiting")
        }
        p.editRegisteredProject(
            EditRegisteredProjectDto(
                id = null,
                name = null,
                shortContent = null,
                haveSeenCount = p.haveSeenCount + 1,
                status = null,
                purpose = null,
                theoreticalBackground = null,
                processList = null,
                result = null,
                conclusion = null,
                referenceList = null,
                creationList = null,
                codeLinkList = null,
                tagList = null
        ))
        return MaximumProjectResponse(
            name = p.name,
            imageLink = p.imageLinkList,
            createBy = p.createdBy,
            updateBy = p.updatedBy,
            createAt = p.createdDate,
            updateAt = p.updateDate,
            projectStatus = p.status,
            shortContent = p.shortContent,
            haveSeenCount = p.haveSeenCount,
            githubLinkList = p.codeLinkList
        )
    }

    override fun writeRegisteredProject(request: RegisteredProjectCreateRequest) {
        verifyUser(request.studentIdList)
        val c = request.studentIdList
            .map { creationRepository.save(
                Creation(
                project = null,
                student = userRepository.findByIdOrNull(it)
                    ?: throw UserNotFoundException("$it :: not found")
            ))}.toList().toMutableList()

        val p = registeredProjectRepository.save(
            RegisteredProject(
            name = request.name,
            shortContent = request.shortContent,
            purpose = request.purpose,
            theoreticalBackground = request.theoreticalBackground,
            codeLinkList = request.githubLinkList,
            processList = request.processList,
            result = request.result,
            conclusion = request.conclusion,
            referenceList = request.referenceList,
            tagList = request.tagList,
            creationList = c
        ))
        c.map {
            it.id?.let {it1 -> creationRepository.findByIdOrNull(it1)
                ?.editCreation(project = p)
            }
        }
    }

    override fun editRegisteredProject(request: RegisteredProjectEditRequest) {
        verifyUser(request.studentIdList)

        val p = registeredProjectRepository.findByIdOrNull(request.projectId)
            ?: throw ProjectNotFoundException("${request.projectId} :: not found")

        p.creationList
            .map { it.id?.let { it1 -> creationRepository.deleteById(it1) } }

        val c = request.studentIdList
            .map { creationRepository.save(
                Creation(
                    project = p,
                    student = userRepository.findById(it).orElse(null)
                ) ) }.toList().toMutableList()

        p.editRegisteredProject(
            EditRegisteredProjectDto(
                id = null,
                name = request.name,
                shortContent = request.shortContent,
                haveSeenCount = null,
                status = null,
                purpose = request.purpose,
                theoreticalBackground = request.theoreticalBackground,
                processList = request.processList,
                result = request.result,
                conclusion = request.conclusion,
                referenceList = request.referenceList,
                creationList = c,
                codeLinkList = request.githubLinkList,
                tagList = request.tagList
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