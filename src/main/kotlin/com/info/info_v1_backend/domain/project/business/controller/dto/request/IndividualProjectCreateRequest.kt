package com.info.info_v1_backend.domain.project.business.controller.dto.request

data class IndividualProjectCreateRequest(
    val imageLinkList: MutableList<String>?,
    val name: String,
    val shortContent: String,
    val githubLinkList: MutableList<String>,
    val studentIdList: List<Long>,
    val tagList: MutableList<String>
)
