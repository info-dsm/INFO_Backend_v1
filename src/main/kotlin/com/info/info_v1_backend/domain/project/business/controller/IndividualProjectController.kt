package com.info.info_v1_backend.domain.project.business.controller

import com.info.info_v1_backend.domain.project.business.controller.dto.ProjectListResponse
import com.info.info_v1_backend.domain.project.business.service.IndividualProjectService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/info/v1/individual")
class IndividualProjectController(private val individualProjectService: IndividualProjectService) {
    @GetMapping("/project-list/latest-order}")
    fun getMinimumLatestOrderProjectList(): ProjectListResponse {
        return individualProjectService.getMinimumLatestOrderProjectList();
    }
    @GetMapping("/project-list/number-of-views-order")
    fun getMinimumNumberOfViewsProjectList(): ProjectListResponse {
        return individualProjectService.getMinimumNumberOfViewsProjectList()
    }
}