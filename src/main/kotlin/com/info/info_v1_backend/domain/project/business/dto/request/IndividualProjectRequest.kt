package com.info.info_v1_backend.domain.project.business.dto.request

import com.info.info_v1_backend.domain.project.data.entity.Creation

data class IndividualProjectRequest(
    val imageLinkList: MutableList<String>?,
    val name: String,
    val shortContent: String,
    val githubLinkList: MutableList<String>,
    val creationList: MutableList<Creation>,
    val tagList: MutableList<String>
)
