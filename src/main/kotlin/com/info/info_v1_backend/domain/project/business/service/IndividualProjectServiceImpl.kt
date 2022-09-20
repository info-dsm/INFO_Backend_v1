package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.auth.data.repository.user.StudentRepository
import com.info.info_v1_backend.domain.auth.exception.UserNotFoundException
import com.info.info_v1_backend.domain.project.business.dto.common.StudentIdDto
import com.info.info_v1_backend.domain.project.business.dto.request.EditIndividualProjectDto
import com.info.info_v1_backend.domain.project.business.dto.request.IndividualProjectCreateRequest
import com.info.info_v1_backend.domain.project.business.dto.request.IndividualProjectEditRequest
import com.info.info_v1_backend.domain.project.business.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.dto.response.MinimumProjectResponse
import com.info.info_v1_backend.domain.project.data.entity.Creation
import com.info.info_v1_backend.domain.project.data.entity.project.IndividualProject
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
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.stream.Collectors

@Service
class IndividualProjectServiceImpl(
    private val individualRepository: ProjectRepository<IndividualProject>,
    private val currentUtil: CurrentUtil,
    private val userRepository: StudentRepository,
    private val creationRepository: CreationRepository,
    private val s3Util: S3Util,
    private val fileRepository: FileRepository
): IndividualProjectService{

    override fun getMinimumLatestOrderProjectList(idx: Int, size: Int): Page<MinimumProjectResponse> {
        //security config에서 url로 authentication필요
        return individualRepository.findAllByProjectStatus(
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

    override fun getMinimumNumberOfViewsProjectList(idx: Int, size: Int): Page<MinimumProjectResponse> {
        //security config에서 url로 authentication필요
        return individualRepository.findAllByProjectStatus(
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
                status = null,
                photoList = null
            ))
        return MaximumProjectResponse(
            imagLinkList = p.photoList?.map {
                it.toImageDto()
            }?.toMutableList(),
            name = p.name,
            status = p.status,
            createAt = p.createdAt,
            updateAt = p.updatedAt,
            createBy = p.createdBy,
            updateBy = p.updatedBy,
            shortContent = p.shortContent,
            codeLinkList = p.codeLinkList,
            haveSeenCount = p.haveSeenCount
        )
    }

    override fun writeIndividualProject(request: IndividualProjectCreateRequest) {
        verifyUser(request.studentIdList)
        val c = request.studentIdList.stream()
            .map { creationRepository.save(Creation(
                project = null,
                student = userRepository.findByIdOrNull(it.studentId)
                    ?: throw UserNotFoundException("$it :: not found")
            ))}
            .collect(Collectors.toList())

        val p = individualRepository.save(IndividualProject(
            name = request.name,
            shortContent = request.shortContent,
            codeLinkList = request.githubLinkList,
            tagList = request.tagList,
            creationList = c,
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
                    student = userRepository.findById(it.studentId).orElse(null)
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
                status = null,
                photoList = null
            )
        )
    }

    override fun deleteProject(projectId: Long) {
        val p = individualRepository.findByIdOrNull(projectId)
            ?: throw ProjectNotFoundException("$projectId :: 없는 프로젝트 입니다")
        if(p.creationList?.stream()
                ?.anyMatch { it.student == currentUtil.getCurrentUser() } == true
        ){
            throw ForbiddenException("$projectId :: 프로젝트에 접근 권한이 없습니다")
        }
        individualRepository.deleteById(projectId)
    }

    private fun verifyUser(studentIdList: MutableList<StudentIdDto>) {
        if(studentIdList.stream()
                .anyMatch { currentUtil.getCurrentUser() == userRepository.findById(it.studentId) }){
            throw NotHaveAccessProjectException("작성자가 프로젝트에 참여하지 않음")
        }
    }

    override fun uploadImage(image: MultipartFile, projectId: Long) {
        val project = individualRepository.findByIdOrNull(projectId)
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
            project.addImage(f)
        } else {
            throw ForbiddenException("$projectId :: 프로젝트에 대한 권한이 없습니다")
        }
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