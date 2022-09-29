package com.info.info_v1_backend.domain.project.business.dto.response

import com.info.info_v1_backend.domain.project.business.dto.common.StudentIdDto
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import com.info.info_v1_backend.global.file.dto.FileResponse
import java.time.LocalDateTime

data class MaximumProjectResponse(
    val name: String,
    val status: ProjectStatus,
    val createAt: LocalDateTime?,
    val updateAt: LocalDateTime?,
    val createBy: Long?,
    val updateBy: Long?,
    val shortContent: String,
    val haveSeenCount: Long,
    val codeLinkList: MutableList<String>?,
    val studentIdList: MutableList<StudentIdDto>?
)
