package com.info.info_v1_backend.domain.project.business.controller.dto.response

import com.info.info_v1_backend.domain.project.business.controller.dto.data.GithubLinkDto
import com.info.info_v1_backend.infra.amazon.s3.dto.ImageDto
import java.time.LocalDateTime

data class MinimumProjectResponse(
    val imageLinkList: MutableList<ImageDto>,
    val projectId: Long,
    val name: String,
    val githubLinkList: MutableList<GithubLinkDto>,
    val createAt: LocalDateTime?,
    val updateAt: LocalDateTime?,
    val createdBy: Long?,
    val updatedBy: Long?,
    val shortContent: String,
    val haveSeenCount: Long,
)
