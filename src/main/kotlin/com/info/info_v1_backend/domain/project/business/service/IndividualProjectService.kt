package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.project.business.controller.dto.request.IndividualProjectCreateRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.request.IndividualProjectEditRequest
import com.info.info_v1_backend.domain.project.business.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.dto.response.MinimumProjectListResponse

interface IndividualProjectService {

    fun getMinimumLatestOrderProjectList(): MinimumProjectListResponse

    fun getMinimumNumberOfViewsProjectList(): MinimumProjectListResponse

    fun getMaximumProject(id: Long): MaximumProjectResponse

    fun writeIndividualProject(request: IndividualProjectCreateRequest)

    fun editIndividualProject(request: IndividualProjectEditRequest)

}