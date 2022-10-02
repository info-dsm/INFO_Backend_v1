package com.info.info_v1_backend.global.file.dto

import com.info.info_v1_backend.global.file.entity.type.FileType

data class FileResponse(
    val fileId: Long,
    val fileUrl: String,
    val fileType: FileType,
    val extension: String
)
