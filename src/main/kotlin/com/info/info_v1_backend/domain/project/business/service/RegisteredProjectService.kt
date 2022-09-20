package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.project.business.dto.request.ProjectStatusEditRequest
import com.info.info_v1_backend.domain.project.business.dto.request.RegisteredProjectCreateRequest
import com.info.info_v1_backend.domain.project.business.dto.request.RegisteredProjectEditRequest
import com.info.info_v1_backend.domain.project.business.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.dto.response.MinimumProjectResponse
import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile

interface RegisteredProjectService {

    fun getMinimumNumberOfViewsProjectList(idx: Int, size: Int): Page<MinimumProjectResponse>

    fun getMinimumLatestOrderProjectList(idx: Int, size: Int): Page<MinimumProjectResponse>

    fun getMaximumProject(id: Long): MaximumProjectResponse

    fun writeRegisteredProject(request: RegisteredProjectCreateRequest)

    fun editRegisteredProject(request: RegisteredProjectEditRequest)

    fun getWaitingMinimumProject()

    fun updateStatus(request: ProjectStatusEditRequest)

    fun uploadImage(image: MultipartFile, projectId: Long)

    fun deleteProject(projectId: Long)
    fun deleteImage(imageId: Long)
}