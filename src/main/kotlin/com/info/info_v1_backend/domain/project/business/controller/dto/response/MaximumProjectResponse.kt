package com.info.info_v1_backend.domain.project.business.controller.dto.response

import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import com.info.info_v1_backend.global.image.entity.File
import java.time.LocalDateTime

data class MaximumProjectResponse(
    val imageLink: MutableList<File>?,
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
