package com.info.info_v1_backend.domain.project.business.controller.dto.request

import com.info.info_v1_backend.domain.project.business.controller.dto.data.*

data class RegisteredProjectCreateRequest(
    val imageLinkList: MutableList<ImageLink>?,
    val name: String,
    val shortContent: String,
    val purpose: String,
    val theoreticalBackground: MutableList<TheoreticalBackgroundDto>,
    val processList: MutableList<ProcessDto>,
    val result: String,
    val conclusion: String,
    val referenceList: MutableList<ReferenceDto>,
    val githubLinkList: MutableList<GithubLinkDto>,
    val studentIdList: MutableList<StudentIdDto>,
    val tagList: MutableList<TagDto>
)
