package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.project.business.dto.request.IndividualProjectCreateRequest
import com.info.info_v1_backend.domain.project.business.dto.request.IndividualProjectEditRequest
import com.info.info_v1_backend.domain.project.business.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.dto.response.MinimumProjectResponse
import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile

interface IndividualProjectService {

    fun getMinimumLatestOrderProjectList(idx: Int, size: Int): Page<MinimumProjectResponse>

    fun getMinimumNumberOfViewsProjectList(idx: Int, size: Int): Page<MinimumProjectResponse>

    fun getMaximumProject(id: Long): MaximumProjectResponse

    fun uploadImage(image: MultipartFile, projectId: Long)

    fun writeIndividualProject(request: IndividualProjectCreateRequest)

    fun editIndividualProject(request: IndividualProjectEditRequest)

    fun deleteProject(projectId: Long)

    fun deleteImage(imageId: Long)

}