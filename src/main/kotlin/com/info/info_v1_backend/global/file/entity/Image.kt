package com.info.info_v1_backend.global.file.entity

import com.info.info_v1_backend.global.file.entity.type.FileType
import javax.persistence.DiscriminatorValue

@DiscriminatorValue("image")
class Image(
    fileUrl: String,
    extension: String,

): File(
    fileUrl,
    FileType.IMAGE,
    extension,

) {
}