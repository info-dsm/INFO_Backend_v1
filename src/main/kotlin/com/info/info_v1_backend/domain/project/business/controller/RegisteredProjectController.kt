package com.info.info_v1_backend.domain.project.business.controller

import com.info.info_v1_backend.domain.project.business.controller.dto.ProjectListResponse
import com.info.info_v1_backend.domain.project.business.service.RegisteredProjectService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/info/v1/registered")
class RegisteredProjectController(private val registeredProjectService: RegisteredProjectService) {
    @GetMapping("/project-list/latest-order}")
    fun getMinimumLatestOrderProjectList(): ProjectListResponse{
        return registeredProjectService.getMinimumLatestOrderProjectList();
    }
    @GetMapping("/project-list/number-of-views-order")
    fun getMinimumNumberOfViewsProjectList(): ProjectListResponse{
        return registeredProjectService.getMinimumNumberOfViewsProjectList()
    }
}