package com.info.info_v1_backend.domain.company.data.entity.notice

import com.info.info_v1_backend.global.file.entity.File
import com.info.info_v1_backend.global.file.entity.type.FileType
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


@Entity
@DiscriminatorValue("reporter")
class Reporter(
    fileUrl: String,
    fileType: FileType,
    extension: String,
    notice: Notice
): File(
    fileUrl,
    fileType,
    extension
) {
    @ManyToOne
    @JoinColumn(name = "notice_id")
    var notice: Notice = notice
        protected set

}