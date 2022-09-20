package com.info.info_v1_backend.domain.project.business.controller.dto.data

import com.info.info_v1_backend.global.image.entity.type.FileType

data class ImageRequestDto(
    val fileUrl: String,
    val fileType: FileType,
    val extension: String,
)
