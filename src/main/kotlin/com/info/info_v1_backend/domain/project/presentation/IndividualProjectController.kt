package com.info.info_v1_backend.domain.project.presentation

import com.info.info_v1_backend.domain.project.business.dto.request.IndividualProjectCreateRequest
import com.info.info_v1_backend.domain.project.business.dto.request.IndividualProjectEditRequest
import com.info.info_v1_backend.domain.project.business.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.dto.response.MinimumProjectResponse
import com.info.info_v1_backend.domain.project.business.service.IndividualProjectService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/info/v1/project/individual")
class IndividualProjectController(
    private val individualProjectService: IndividualProjectService
    ) {

    @GetMapping("/minimum/latest-order/{idx}/{size}")
    fun getMinimumLatestOrderProjectList(
        @PathVariable("idx") idx: Int = 0,
        @PathVariable("size") size: Int = 0
    ): Page<MinimumProjectResponse> {
        return individualProjectService.getMinimumLatestOrderProjectList(idx, size);
    }

    @GetMapping("/minimum/number-of-views-order/{idx}/{size}")
    fun getMinimumNumberOfViewsProjectList(
        @PathVariable("idx") idx: Int = 0,
        @PathVariable("size") size: Int = 0
    ): Page<MinimumProjectResponse> {
        return individualProjectService.getMinimumNumberOfViewsProjectList(idx, size)
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

    @DeleteMapping("/{project-id}")
    fun deleteProject(@PathVariable("project-id") projectId: Long){
        individualProjectService.deleteProject(projectId)
    }

    @PostMapping("/image/{project-id}")
    @ResponseStatus(HttpStatus.CREATED)
    fun uploadImage(
        @RequestPart image: MultipartFile,
        @PathVariable("project-id") projectId: Long
    ) {
        individualProjectService.uploadImage(image, projectId)
    }

    @DeleteMapping("/image/{image-id}")
    fun deleteImage(@PathVariable("image-id") imageId: Long){
        individualProjectService.deleteImage(imageId)
    }
}