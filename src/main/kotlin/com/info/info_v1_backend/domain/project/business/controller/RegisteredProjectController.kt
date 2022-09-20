package com.info.info_v1_backend.domain.project.business.controller

import com.info.info_v1_backend.domain.project.business.controller.dto.request.ProjectStatusEditRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.request.RegisteredProjectCreateRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.request.RegisteredProjectEditRequest
import com.info.info_v1_backend.domain.project.business.controller.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.controller.dto.response.MinimumProjectResponse
import com.info.info_v1_backend.domain.project.business.service.RegisteredProjectService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/info/v1/project/registered")
class RegisteredProjectController(private val registeredProjectService: RegisteredProjectService) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun writeRegisteredProject(
        @RequestBody
        request: RegisteredProjectCreateRequest
    ){
        registeredProjectService.writeRegisteredProject(request)
    }

    @PatchMapping
    fun editRegisteredProject(@RequestBody request: RegisteredProjectEditRequest){
        registeredProjectService.editRegisteredProject(request)
    }

    @GetMapping("/minimum/latest-order/{idx}/{size}")
    fun getMinimumLatestOrderProjectList(
        @PathVariable("idx") idx: Int = 0,
        @PathVariable("size") size: Int = 0
    ):  Page<MinimumProjectResponse> {
        return registeredProjectService.getMinimumLatestOrderProjectList(idx, size)
    }

    @GetMapping("/minimum/number-of-views-order/{idx}/{size}")
    fun getMinimumNumberOfViewsProjectList(
        @PathVariable("idx") idx: Int = 0,
        @PathVariable("size") size: Int = 0
    ):  Page<MinimumProjectResponse> {
        return registeredProjectService.getMinimumNumberOfViewsProjectList(idx, size)
    }

    @GetMapping("/maximum/{project-id}")
    fun getMaximumProject(@PathVariable("project-id") id: Long): MaximumProjectResponse {
        return registeredProjectService.getMaximumProject(id)
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