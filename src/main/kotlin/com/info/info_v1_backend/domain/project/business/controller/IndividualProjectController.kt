package com.info.info_v1_backend.domain.project.business.controller

import com.info.info_v1_backend.domain.project.business.controller.dto.request.IndividualProjectCreateRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.request.IndividualProjectEditRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.controller.dto.response.MinimumProjectListResponse
import com.info.info_v1_backend.domain.project.business.service.IndividualProjectService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/info/v1/project/individual")
class IndividualProjectController(private val individualProjectService: IndividualProjectService) {

    @GetMapping("/minimum/latest-order}")
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
    fun writeIndividualProject(@RequestBody request: IndividualProjectCreateRequest){
        individualProjectService.writeIndividualProject(request)
    }

    @PatchMapping
    fun editIndividualProject(@RequestBody request: IndividualProjectEditRequest){
        individualProjectService.editIndividualProject(request)
    }
}