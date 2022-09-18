package com.info.info_v1_backend.global.image.entity

import com.info.info_v1_backend.global.base.entity.BaseAuthorEntity
import com.info.info_v1_backend.global.image.entity.type.FileType
import com.info.info_v1_backend.infra.amazon.s3.dto.ImageDto
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class File(
    fileUrl: String,
    fileType: FileType,
    extension: String

): BaseAuthorEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "file_url", nullable = false)
    var fileUrl: String = fileUrl

    @Column(name = "file_type", nullable = false)
    var fileType: FileType = fileType
        protected set

    @Column(name = "extension", nullable = false)
    var extention: String = extension
        protected set

    fun toImageDto(): ImageDto {
        return ImageDto(
            this.fileUrl,
            this.id!!
        )
    }

}