package com.info.info_v1_backend.domain.project.business.controller

import com.info.info_v1_backend.domain.project.business.controller.dto.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.controller.dto.MinimumProjectListResponse
import com.info.info_v1_backend.domain.project.business.service.RegisteredProjectService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/info/v1/registered")
class RegisteredProjectController(private val registeredProjectService: RegisteredProjectService) {

    @GetMapping("/project-list/latest-order}")
    fun getMinimumLatestOrderProjectList(): MinimumProjectListResponse{
        return registeredProjectService.getMinimumLatestOrderProjectList();
    }

    @GetMapping("/project-list/number-of-views-order")
    fun getMinimumNumberOfViewsProjectList(): MinimumProjectListResponse{
        return registeredProjectService.getMinimumNumberOfViewsProjectList()
    }

    @GetMapping("/project/{project-id}")
    fun getMaximumProject(@PathVariable("project-id") id: Long): MaximumProjectResponse{
        return registeredProjectService.getMaximumProject(id)
    }
}