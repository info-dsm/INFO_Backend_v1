package com.info.info_v1_backend.domain.project.business.controller.dto.request

import com.info.info_v1_backend.domain.project.business.controller.dto.data.ImageRequestDto
import com.info.info_v1_backend.domain.project.business.controller.dto.data.StudentIdDto

data class RegisteredProjectCreateRequest(
    val imageLinkList: MutableList<ImageRequestDto>?,
    val name: String,
    val shortContent: String,
    val purpose: String,
    val theoreticalBackground: MutableList<String>,
    val processList: MutableList<String>,
    val result: String,
    val conclusion: String,
    val referenceList: MutableList<String>,
    val studentList: MutableList<StudentIdDto>,
    val codeLinkList: MutableList<String>,
    val tagList: MutableList<String>
)
