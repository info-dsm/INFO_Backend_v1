package com.info.info_v1_backend.domain.project.business.dto.response

import java.time.LocalDateTime

data class MinimumProjectResponse(
    val name: String,
    val createAt: LocalDateTime?,
    val updateAt: LocalDateTime?,
    val createdBy: Long?,
    val updatedBy: Long?,
    val shortContent: String,
    val haveSeenCount: Long,
    val githubLinkList: MutableList<String>
)
