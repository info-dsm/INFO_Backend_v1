package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.project.business.controller.dto.ProjectListResponse

interface RegisteredProjectService {
    fun getMinimumLatestOrderProjectList(): ProjectListResponse
    fun getMinimumNumberOfViewsProjectList(): ProjectListResponse
}