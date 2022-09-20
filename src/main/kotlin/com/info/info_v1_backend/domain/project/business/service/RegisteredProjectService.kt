package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.project.business.controller.dto.request.ProjectStatusEditRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.request.RegisteredProjectCreateRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.request.RegisteredProjectEditRequest
import com.info.info_v1_backend.domain.project.business.dto.request.SortCriteriaType
import com.info.info_v1_backend.domain.project.business.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.dto.response.MinimumProjectResponse
import org.springframework.data.domain.Page

interface RegisteredProjectService {

    fun getApprovedMinimumProjectList(idx: Int, size: Int, sortType: SortCriteriaType): Page<MinimumProjectResponse>

    fun getMaximumProject(id: Long): MaximumProjectResponse

    fun writeRegisteredProject(request: RegisteredProjectCreateRequest)

    fun editRegisteredProject(request: RegisteredProjectEditRequest)

    fun getWaitingMinimumProject()

    fun updateStatus(request: ProjectStatusEditRequest)

}