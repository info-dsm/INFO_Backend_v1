package com.info.info_v1_backend.domain.company.data.entity.notice.file

import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.global.file.entity.File
import com.info.info_v1_backend.global.file.entity.type.FileType
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


@Entity
@DiscriminatorValue("reporter")
@Where(clause = "file_is_deleted = false")
@SQLDelete(sql = "UPDATE `file` SET file_is_deleted = true where id = ?")
class Reporter(
    fileUrl: String,
    fileType: FileType,
    extension: String,
    notice: Notice,
    user: User
): File(
    fileUrl,
    fileType,
    extension
) {
    @ManyToOne
    @JoinColumn(name = "notice_id")
    var notice: Notice = notice
        protected set

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User = user
        protected set

}