package com.info.info_v1_backend.global.file.dto

import com.info.info_v1_backend.global.file.entity.type.FileType

data class FileDto(
    val fileUrl: String,
    val fileType: FileType,
    val extension: String,
    val fileName: String
) {

    override fun toString(): String {
        return "url: $fileUrl, type: $fileType, extension: $extension"
    }
}
