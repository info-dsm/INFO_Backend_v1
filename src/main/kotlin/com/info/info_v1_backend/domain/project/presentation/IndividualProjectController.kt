package com.info.info_v1_backend.domain.project.presentation

import com.info.info_v1_backend.domain.project.business.dto.request.IndividualProjectRequest
import com.info.info_v1_backend.domain.project.business.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.dto.response.MinimumProjectListResponse
import com.info.info_v1_backend.domain.project.business.service.IndividualProjectService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/info/v1/project/individual")
class IndividualProjectController(private val individualProjectService: IndividualProjectService) {

    @GetMapping("/minimum/latest-order")
    fun getMinimumLatestOrderProjectList(): MinimumProjectListResponse {
        return individualProjectService.getMinimumLatestOrderProjectList();
    }

    @GetMapping("/minimum/number-of-views-order")
    fun getMinimumNumberOfViewsProjectList(): MinimumProjectListResponse {
        return individualProjectService.getMinimumNumberOfViewsProjectList()
    }

    @GetMapping("/maximum/{project-id}")
    fun getMaximumProject(@PathVariable("project-id") id: Long): MaximumProjectResponse {
        return individualProjectService.getMaximumProject(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun writeIndividualProject(@RequestBody request: IndividualProjectRequest){
        individualProjectService.writeIndividualProject(request)
    }
}