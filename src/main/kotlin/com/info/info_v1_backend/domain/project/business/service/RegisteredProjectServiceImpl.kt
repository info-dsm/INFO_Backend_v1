package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import com.info.info_v1_backend.domain.auth.data.repository.user.StudentRepository
import com.info.info_v1_backend.domain.auth.exception.UserNotFoundException
import com.info.info_v1_backend.domain.project.business.controller.dto.request.EditRegisteredProjectDto
import com.info.info_v1_backend.domain.project.business.controller.dto.request.ProjectStatusEditRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.request.RegisteredProjectCreateRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.request.RegisteredProjectEditRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.response.*
import com.info.info_v1_backend.domain.project.data.entity.Creation
import com.info.info_v1_backend.domain.project.data.entity.project.RegisteredProject
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import com.info.info_v1_backend.domain.project.data.repository.CreationRepository
import com.info.info_v1_backend.domain.project.data.repository.ProjectRepository
import com.info.info_v1_backend.domain.project.exception.NotHaveAccessProjectException
import com.info.info_v1_backend.domain.project.exception.ProjectNotFoundException
import com.info.info_v1_backend.global.error.common.ForbiddenException
import com.info.info_v1_backend.global.util.user.CurrentUtil
import io.undertow.util.BadRequestException
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

    override fun getApprovedMinimumProjectList(idx: Int, size: Int, sortType: SortCriteriaType): Page<MinimumProjectResponse> {
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
        }
    }

    override fun getMaximumProject(id: Long): MaximumProjectResponse {
        //security config에서 url로 authentication필요
        val p = registeredProjectRepository.findByIdOrNull(id)
            ?: throw ProjectNotFoundException("$id :: not found")
        if (p.status == ProjectStatus.WAITING) {
            verifyAuth()
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
        )
        )
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

    override fun writeRegisteredProject(request: RegisteredProjectCreateRequest) {
        verifyAuth(request.studentIdList)
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
        verifyAuth(request.studentIdList)

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

    override fun getWaitingMinimumProject() {
        verifyAuth()
        val list = registeredProjectRepository.findAll(Sort.by(Sort.Direction.ASC, "createAt"))
            .filter { it.status == ProjectStatus.WAITING }
            .map {
                    WaitingMinimumProjectResponse(
                        id = it.id?: throw BadRequestException("$it :: id가 null입니다"),
                        name = it.name
                    )
            }
            .toMutableList()
        WaitingMinimumListProjectResponse(list)
    }

    override fun updateStatus(request: ProjectStatusEditRequest) {
        verifyAuth()
        val p = registeredProjectRepository.findByIdOrNull(request.projectId)
            ?: throw ProjectNotFoundException("${request.projectId} :: not found")
        p.editRegisteredProject(
            EditRegisteredProjectDto(
                id = null,
                name = null,
                shortContent = null,
                haveSeenCount = null,
                status = if(request.status) ProjectStatus.APPROVE else ProjectStatus.REJECT,
                purpose = null,
                theoreticalBackground = null,
                processList = null,
                result = null,
                conclusion = null,
                referenceList = null,
                creationList = null,
                codeLinkList = null,
                tagList = null
            )
        )
    }

    private fun verifyAuth(studentIdList: List<Long>) {
        if(studentIdList.stream()
                .anyMatch { currentUtil.getCurrentUser() == userRepository.findById(it) }){
            throw NotHaveAccessProjectException("작성자가 프로젝트에 참여하지 않음")
        }
    }

    private fun verifyAuth(){
        if(!currentUtil.getCurrentUser().roleList
                .stream()
                .anyMatch { it == Role.TEACHER }){
            throw ForbiddenException("${currentUtil.getCurrentUser()} :: 권한 오류")
        }
    }
}