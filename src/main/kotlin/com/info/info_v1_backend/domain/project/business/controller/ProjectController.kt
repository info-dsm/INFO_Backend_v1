package com.info.info_v1_backend.domain.project.business.controller

import com.info.info_v1_backend.domain.project.business.service.IndividualProjectService
import com.info.info_v1_backend.domain.project.business.service.RegisteredProjectService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/info/v1")
class ProjectController(private val individualProjectService: IndividualProjectService,
                        private val registeredProjectService: RegisteredProjectService
) {
}