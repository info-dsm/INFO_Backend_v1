package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.project.business.controller.dto.request.RegisteredProjectCreateRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.request.RegisteredProjectEditRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.controller.dto.response.MinimumProjectListResponse

interface RegisteredProjectService {

    fun getMinimumLatestOrderProjectList(): MinimumProjectListResponse

    fun getMinimumNumberOfViewsProjectList(): MinimumProjectListResponse

    fun getMaximumProject(id: Long): MaximumProjectResponse

    fun writeRegisteredProject(request: RegisteredProjectCreateRequest)

    fun editRegisteredProject(request: RegisteredProjectEditRequest)

    fun getWaitingMinimumProject()

}