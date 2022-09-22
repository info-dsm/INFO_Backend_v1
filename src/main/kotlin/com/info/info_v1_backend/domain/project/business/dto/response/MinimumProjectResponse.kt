package com.info.info_v1_backend.domain.project.business.dto.response

import com.info.info_v1_backend.domain.project.business.dto.common.StudentIdDto
import com.info.info_v1_backend.infra.amazon.s3.dto.ImageDto

data class MinimumProjectResponse(
    val photoList: MutableList<ImageDto>?,
    val projectId: Long,
    val codeLinkList: MutableList<String>?,
    val shortContent : String,
    val studentIdList: MutableList<StudentIdDto>,
    val haveSeenCount: Long
)
