package com.info.info_v1_backend.domain.project.presentation

import com.info.info_v1_backend.domain.project.business.dto.request.ProjectStatusEditRequest
import com.info.info_v1_backend.domain.project.business.dto.request.RegisteredProjectCreateRequest
import com.info.info_v1_backend.domain.project.business.dto.request.RegisteredProjectEditRequest
import com.info.info_v1_backend.domain.project.business.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.dto.response.MinimumProjectResponse
import com.info.info_v1_backend.domain.project.business.service.RegisteredProjectService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/info/v1/project/registered")
class RegisteredProjectController(
    private val registeredProjectService: RegisteredProjectService
    ) {

    @GetMapping("/minimum/latest-order/{idx}/{size}")
    fun getMinimumLatestOrderProjectList(
        @PathVariable("idx") idx: Int = 0,
        @PathVariable("size") size: Int = 0,
    ): Page<MinimumProjectResponse> {
        return registeredProjectService.getMinimumLatestOrderProjectList(idx, size)
    }

    @GetMapping("/minimum/number-of-views-order/{idx}/{size}")
    fun getMinimumNumberOfViewsProjectList(
        @PathVariable("idx") idx: Int = 0,
        @PathVariable("size") size: Int = 0,
    ): Page<MinimumProjectResponse> {
        return registeredProjectService.getMinimumNumberOfViewsProjectList(idx, size)
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

    @PostMapping("/status")
    fun updateStatus(@RequestBody request: ProjectStatusEditRequest){
        registeredProjectService.updateStatus(request)
    }

//    @GetMapping("/waiting")
//    fun getWaitingMinimumProject(){
//        registeredProjectService.getWaitingMinimumProject()
//    }

    @DeleteMapping("/{project-id}")
    fun deleteProject(@PathVariable("project-id") projectId: Long){
        registeredProjectService.deleteProject(projectId)
    }

    @PostMapping("/image/{project-id}")
    @ResponseStatus(HttpStatus.CREATED)
    fun uploadImage(
        @RequestPart image: MultipartFile,
        @PathVariable("project-id") projectId: Long
    ) {
        registeredProjectService.uploadImage(image, projectId)
    }

    @DeleteMapping("/image/{image-id}")
    fun deleteImage(@PathVariable("image-id") imageId: Long){
        registeredProjectService.deleteImage(imageId)
    }
}