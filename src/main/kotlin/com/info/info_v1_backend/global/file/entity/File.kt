package com.info.info_v1_backend.global.file.entity

import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.project.data.entity.project.Project
import com.info.info_v1_backend.global.base.entity.BaseAuthorEntity
import com.info.info_v1_backend.global.file.entity.type.FileType
import com.info.info_v1_backend.infra.amazon.s3.dto.ImageDto
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "file_type")
class File(
    fileUrl: String,
    fileType: FileType,
    extension: String,
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