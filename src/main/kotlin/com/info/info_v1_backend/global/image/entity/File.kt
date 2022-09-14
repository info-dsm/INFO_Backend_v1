package com.info.info_v1_backend.global.image.entity

import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import com.info.info_v1_backend.global.image.entity.type.FileType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class File(
    fileKey: String,
    fileType: FileType,
    extention: String

): BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "file_key", nullable = false)
    var fileKey: String = fileKey
        protected set

    @Column(name = "file_type", nullable = false)
    var fileType: FileType = fileType
        protected set

    @Column(name = "extention", nullable = false)
    var extention: String = extention
        protected set


}