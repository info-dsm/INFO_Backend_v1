package com.info.info_v1_backend.domain.company.data.entity.notice.file

import com.fasterxml.jackson.annotation.JsonIgnore
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.notice.applicant.Applicant
import com.info.info_v1_backend.global.file.dto.FileDto
import com.info.info_v1_backend.global.file.entity.File
import com.info.info_v1_backend.global.file.entity.type.FileType
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.JoinColumns
import javax.persistence.ManyToOne


@Entity
@DiscriminatorValue("reporter")
@Where(clause = "file_is_deleted = false")
@SQLDelete(sql = "UPDATE `file` SET file_is_deleted = true where id = ?")
class Reporter(
    dto: FileDto,
    applicant: Applicant,
): File(
    dto.fileUrl,
    dto.fileType,
    dto.extension
) {
    @ManyToOne
    @JoinColumns(
        JoinColumn(name = "student_id"),
        JoinColumn(name = "notice_id")
    )
    @JsonIgnore
    var applicant: Applicant = applicant
        protected set


}