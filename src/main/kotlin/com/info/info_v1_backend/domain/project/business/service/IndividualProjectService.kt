package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.project.business.controller.dto.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.controller.dto.MinimumProjectListResponse

interface IndividualProjectService {

    fun getMinimumLatestOrderProjectList(): MinimumProjectListResponse

    fun getMinimumNumberOfViewsProjectList(): MinimumProjectListResponse

    fun getMaximumProject(id: Long): MaximumProjectResponse

}