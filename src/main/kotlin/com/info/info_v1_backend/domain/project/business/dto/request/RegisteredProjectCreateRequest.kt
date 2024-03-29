package com.info.info_v1_backend.domain.project.business.dto.request

import com.info.info_v1_backend.domain.project.business.dto.common.StudentIdDto

data class RegisteredProjectCreateRequest(
    val name: String,
    val shortContent: String,
    val purpose: String,
    val theoreticalBackground: MutableList<String>,
    val processList: MutableList<String>,
    val result: String,
    val conclusion: String,
    val referenceList: MutableList<String>,
    val codeLinkList: MutableList<String>,
    val studentIdList: MutableList<StudentIdDto>,
    val tagList: MutableList<String>
)
