package com.info.info_v1_backend.global.file.entity

import com.info.info_v1_backend.global.base.entity.BaseAuthorEntity
import com.info.info_v1_backend.global.file.dto.FileResponse
import com.info.info_v1_backend.global.file.entity.type.FileType
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "file_type")
@Table(name = "file")
@Where(clause = "file_is_deleted = false")
@SQLDelete(sql = "UPDATE `file` SET file_is_deleted = true where id = ?")
abstract class File(
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

    @Column(name = "file_extension", nullable = false)
    var extention: String = extension
        protected set

    @Column(name = "file_is_deleted", nullable = false)
    var isDeleted: Boolean = false
        protected set

    fun toFileResponse(): FileResponse {
        return FileResponse(
            this.id!!,
            this.fileUrl,
            this.fileType,
            this.extention
        )
    }

}