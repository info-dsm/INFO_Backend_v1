package com.info.info_v1_backend.domain.company.data.entity.notice.file

import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.global.file.dto.FileDto
import com.info.info_v1_backend.global.file.entity.File
import com.info.info_v1_backend.global.file.entity.type.FileType
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.CascadeType
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.ManyToOne


@Entity
@DiscriminatorValue("attachment")
@OnDelete(action = OnDeleteAction.CASCADE)
class FormAttachment(
    dto: FileDto,
    notice: Notice,
): File(
    dto.fileUrl,
    dto.fileType,
    dto.extension,
    dto.fileName
) {
    @ManyToOne(cascade = [CascadeType.PERSIST])
    var notice: Notice = notice
        protected set
}