package com.info.info_v1_backend.global.file.entity

import com.info.info_v1_backend.global.file.entity.type.FileType
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity


@Entity
@DiscriminatorValue("image")
@Where(clause = "file_is_deleted = false")
@SQLDelete(sql = "UPDATE `file` SET file_is_deleted = true where id = ?")
class Image(
    fileUrl: String,
    extension: String,

): File(
    fileUrl,
    FileType.IMAGE,
    extension,

) {
}