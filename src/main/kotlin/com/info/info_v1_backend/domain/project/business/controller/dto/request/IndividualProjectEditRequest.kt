package com.info.info_v1_backend.domain.project.business.controller.dto.request

data class IndividualProjectEditRequest(
    val projectId: Long,
    val imageLinkList: MutableList<String>?,
    val name: String,
    val shortContent: String,
    val githubLinkList: MutableList<String>,
    val studentIdList: MutableList<Long>,
    val tagList: MutableList<String>
)
