package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.project.business.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.dto.response.MinimumProjectListResponse

interface RegisteredProjectService {

    fun getMinimumLatestOrderProjectList(): MinimumProjectListResponse

    fun getMinimumNumberOfViewsProjectList(): MinimumProjectListResponse

    fun getMaximumProject(id: Long): MaximumProjectResponse

}