package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import com.info.info_v1_backend.domain.auth.data.repository.user.StudentRepository
import com.info.info_v1_backend.domain.auth.exception.UserNotFoundException
import com.info.info_v1_backend.domain.project.business.dto.common.StudentIdDto
import com.info.info_v1_backend.domain.project.business.dto.request.EditRegisteredProjectDto
import com.info.info_v1_backend.domain.project.business.dto.request.ProjectStatusEditRequest
import com.info.info_v1_backend.domain.project.business.dto.request.RegisteredProjectCreateRequest
import com.info.info_v1_backend.domain.project.business.dto.request.RegisteredProjectEditRequest
import com.info.info_v1_backend.domain.project.business.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.dto.response.MinimumProjectResponse
import com.info.info_v1_backend.domain.project.business.dto.response.WaitingMinimumListProjectResponse
import com.info.info_v1_backend.domain.project.business.dto.response.WaitingMinimumProjectResponse
import com.info.info_v1_backend.domain.project.data.entity.Creation
import com.info.info_v1_backend.domain.project.data.entity.project.RegisteredProject
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import com.info.info_v1_backend.domain.project.data.repository.CreationRepository
import com.info.info_v1_backend.domain.project.data.repository.ProjectRepository
import com.info.info_v1_backend.domain.project.exception.NotHaveAccessProjectException
import com.info.info_v1_backend.domain.project.exception.ProjectNotFoundException
import com.info.info_v1_backend.global.error.common.FileNotFoundException
import com.info.info_v1_backend.global.error.common.ForbiddenException
import com.info.info_v1_backend.global.image.entity.File
import com.info.info_v1_backend.global.image.entity.type.FileType
import com.info.info_v1_backend.global.image.repository.FileRepository
import com.info.info_v1_backend.global.util.user.CurrentUtil
import com.info.info_v1_backend.infra.amazon.s3.S3Util
import io.undertow.util.BadRequestException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class RegisteredProjectServiceImpl(
    private val registeredProjectRepository: ProjectRepository<RegisteredProject>,
    private val currentUtil: CurrentUtil,
    private val userRepository: StudentRepository,
    private val creationRepository: CreationRepository,
    private val s3Util: S3Util,
    private val fileRepository: FileRepository
): RegisteredProjectService {
    override fun getMinimumNumberOfViewsProjectList(idx: Int, size: Int): Page<MinimumProjectResponse> {
        return registeredProjectRepository.findAllByProjectStatus(
            ProjectStatus.APPROVE,
            PageRequest.of(
                idx,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")))
            .map {
                MinimumProjectResponse(
                    projectId = it.projectId,
                    photoList = it.photoList,
                    githubLinkList = it.githubLinkList,
                    shortContent = it.shortContent,
                    studentId = it.studentId,
                    haveSeenCount = it.haveSeenCount
                )
            }
    }

    override fun getMinimumLatestOrderProjectList(idx: Int, size: Int): Page<MinimumProjectResponse> {
        return registeredProjectRepository.findAllByProjectStatus(
            ProjectStatus.APPROVE,
            PageRequest.of(
                idx,
                size,
                Sort.by(Sort.Direction.DESC, "haveSeenCount")))
            .map {
                MinimumProjectResponse(
                    projectId = it.projectId,
                    photoList = it.photoList,
                    githubLinkList = it.githubLinkList,
                    shortContent = it.shortContent,
                    studentId = it.studentId,
                    haveSeenCount = it.haveSeenCount
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
                tagList = null,
                photoList = null
            ))
        return MaximumProjectResponse(
            imagLinkList = p.photoList?.map {
                it.toImageDto()
            }?.toMutableList(),
            name = p.name,
            createAt = p.createdAt,
            updateAt = p.updatedAt,
            createBy = p.createdBy,
            updateBy = p.updatedBy,
            status = p.status,
            shortContent = p.shortContent,
            haveSeenCount = p.haveSeenCount,
            codeLinkList = p.codeLinkList
        )
    }

    override fun writeRegisteredProject(request: RegisteredProjectCreateRequest) {
        verifyAuth(request.studentIdList)
        val c = request.studentIdList
            .map { creationRepository.save(
                Creation(
                    project = null,
                    student = userRepository.findByIdOrNull(it.studentId)
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
            ?.map { it.id?.let { it1 -> creationRepository.deleteById(it1) } }

        val c = request.studentIdList
            .map { creationRepository.save(
                Creation(
                    project = p,
                    student = userRepository.findById(it.studentId).orElse(null)
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
                tagList = request.tagList,
                photoList = null
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
                tagList = null,
                photoList = null
            )
        )
    }

    private fun verifyAuth(studentIdList: MutableList<StudentIdDto>) {
        if(studentIdList.stream()
                .anyMatch { currentUtil.getCurrentUser() == userRepository.findById(it.studentId) }){
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

    override fun uploadImage(image: MultipartFile, projectId: Long) {
        val project = registeredProjectRepository.findByIdOrNull(projectId)
            ?: throw ProjectNotFoundException("$projectId :: 없는 프로젝트 입니다")
        if (project.creationList?.stream()
                ?.anyMatch { it.student == currentUtil.getCurrentUser() } == true
        ) {
            val url = s3Util.uploadFile(image, "project", projectId.toString())
            val fileName = image.originalFilename!!

            val ext: String = fileName.substring(fileName.lastIndexOf(".") + 1)
            val f = fileRepository.save(
                File(
                    fileUrl = url,
                    fileType = FileType.IMAGE,
                    extension = ext,
                    project = project,
                    company = null
                )
            )
            if (project.photoList == null) {
                val list: MutableList<File> = ArrayList()
                list.add(f)
                project.editRegisteredProject(
                    EditRegisteredProjectDto(
                        id = null,
                        photoList = list,
                        name = null,
                        shortContent = null,
                        haveSeenCount = null,
                        creationList = null,
                        codeLinkList = null,
                        tagList = null,
                        status = null,
                        conclusion = null,
                        theoreticalBackground = null,
                        purpose = null,
                        processList = null,
                        result = null,
                        referenceList = null
                    )
                )
            } else {
                val list = project.photoList
                list!!.add(f)
                project.editRegisteredProject(
                    EditRegisteredProjectDto(
                        id = null,
                        photoList = list,
                        name = null,
                        shortContent = null,
                        haveSeenCount = null,
                        creationList = null,
                        codeLinkList = null,
                        tagList = null,
                        status = null,
                        conclusion = null,
                        theoreticalBackground = null,
                        purpose = null,
                        processList = null,
                        result = null,
                        referenceList = null
                    )
                )
            }
        } else {
            throw ForbiddenException("$projectId :: 프로젝트에 대한 권한이 없습니다")
        }
    }

    override fun deleteProject(projectId: Long) {
        val p = registeredProjectRepository.findByIdOrNull(projectId)
            ?: throw ProjectNotFoundException("$projectId :: 없는 프로젝트 입니다")
        if(p.creationList?.stream()
                ?.anyMatch { it.student == currentUtil.getCurrentUser() } == true
        ){
            throw ForbiddenException("$projectId :: 프로젝트에 접근 권한이 없습니다")
        }
        registeredProjectRepository.deleteById(projectId)
    }

    override fun deleteImage(imageId: Long) {
        val i = fileRepository.findByIdOrNull(imageId)
            ?: throw FileNotFoundException("$imageId :: 없는 파일 입니다")
        if(i.project?.creationList?.stream()
                ?.anyMatch { it.student != currentUtil.getCurrentUser() } == true
        ){
            throw ForbiddenException("$imageId :: 파일에 대한 권한이 없습니다")
        }
        fileRepository.deleteById(imageId)
    }
}