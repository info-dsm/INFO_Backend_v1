package com.info.info_v1_backend.domain.project.presentation

import com.info.info_v1_backend.domain.project.business.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.dto.response.MinimumProjectListResponse
import com.info.info_v1_backend.domain.project.business.service.RegisteredProjectService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/info/v1/project/registered")
class RegisteredProjectController(private val registeredProjectService: RegisteredProjectService) {

    @GetMapping("/minimum/latest-order")
    fun getMinimumLatestOrderProjectList(): MinimumProjectListResponse {
        return registeredProjectService.getMinimumLatestOrderProjectList();
    }

    @GetMapping("/minimum/number-of-views-order")
    fun getMinimumNumberOfViewsProjectList(): MinimumProjectListResponse {
        return registeredProjectService.getMinimumNumberOfViewsProjectList()
    }

    @GetMapping("/maximum/{project-id}")
    fun getMaximumProject(@PathVariable("project-id") id: Long): MaximumProjectResponse {
        return registeredProjectService.getMaximumProject(id)
    }
}