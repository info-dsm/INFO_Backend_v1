package com.info.info_v1_backend.domain.project.business.controller.dto.response

import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import com.info.info_v1_backend.global.image.entity.File
import com.info.info_v1_backend.infra.amazon.s3.dto.ImageDto
import java.time.LocalDateTime

data class MaximumProjectResponse(
    val imageLink: MutableList<ImageDto>?,
    val name: String,
    val createAt: LocalDateTime?,
    val updateAt: LocalDateTime?,
    val createBy: Long?,
    val updateBy: Long?,
    val projectStatus: ProjectStatus,
    val shortContent: String,
    val haveSeenCount: Long,
    val studentIdList: MutableList<Long?>,
    val githubLinkList: MutableList<String>?
)
