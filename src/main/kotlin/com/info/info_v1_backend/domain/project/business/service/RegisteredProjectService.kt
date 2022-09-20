package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.project.business.controller.dto.request.ProjectStatusEditRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.request.RegisteredProjectCreateRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.request.RegisteredProjectEditRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.controller.dto.response.MinimumProjectResponse
import org.springframework.data.domain.Page

interface RegisteredProjectService {

    fun getMinimumLatestOrderProjectList(idx: Int, size: Int): Page<MinimumProjectResponse>

    fun getMinimumNumberOfViewsProjectList(idx: Int, size: Int): Page<MinimumProjectResponse>

    fun getMaximumProject(id: Long): MaximumProjectResponse

    fun writeRegisteredProject(request: RegisteredProjectCreateRequest)

    fun editRegisteredProject(request: RegisteredProjectEditRequest)

    fun getWaitingMinimumProject()

    fun updateStatus(request: ProjectStatusEditRequest)

}