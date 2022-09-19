package com.info.info_v1_backend.domain.project.business.controller

import com.info.info_v1_backend.domain.project.business.controller.dto.request.ProjectStatusEditRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.request.RegisteredProjectCreateRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.request.RegisteredProjectEditRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.controller.dto.response.MinimumProjectListResponse
import com.info.info_v1_backend.domain.project.business.service.RegisteredProjectService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/info/v1/project/registered")
class RegisteredProjectController(private val registeredProjectService: RegisteredProjectService) {

    @GetMapping("/minimum/latest-order}")
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun writeRegisteredProject(@RequestBody request: RegisteredProjectCreateRequest){
        registeredProjectService.writeRegisteredProject(request)
    }

    @PatchMapping
    fun editRegisteredProject(@RequestBody request: RegisteredProjectEditRequest){
        registeredProjectService.editRegisteredProject(request)
    }

    @GetMapping("/waiting")
    fun getWaitingMinimumProject(){
        registeredProjectService.getWaitingMinimumProject()
    }

    @PatchMapping("/status")
    fun updateStatus(@RequestBody request: ProjectStatusEditRequest){
        registeredProjectService.updateStatus(request)
    }
}