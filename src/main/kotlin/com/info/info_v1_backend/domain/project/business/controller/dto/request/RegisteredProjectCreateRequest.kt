package com.info.info_v1_backend.domain.project.business.controller.dto.request

data class RegisteredProjectCreateRequest(
    val imageLinkList: MutableList<String>?,
    val name: String,
    val shortContent: String,
    val purpose: String,
    val theoreticalBackground: MutableList<String>,
    val processList: MutableList<String>,
    val result: String,
    val conclusion: String,
    val referenceList: MutableList<String>,
    val githubLinkList: MutableList<String>,
    val studentIdList: List<Long>,
    val tagList: MutableList<String>
)
