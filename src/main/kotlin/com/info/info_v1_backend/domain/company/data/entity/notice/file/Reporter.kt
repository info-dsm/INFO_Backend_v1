package com.info.info_v1_backend.domain.company.data.entity.notice.file

import com.fasterxml.jackson.annotation.JsonIgnore
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.notice.applicant.Applicant
import com.info.info_v1_backend.global.file.dto.FileDto
import com.info.info_v1_backend.global.file.entity.File
import com.info.info_v1_backend.global.file.entity.type.FileType
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*


@Entity
@DiscriminatorValue("reporter")
@OnDelete(action = OnDeleteAction.CASCADE)
class Reporter(
    dto: FileDto,
    applicant: Applicant,
): File(
    dto.fileUrl,
    dto.fileType,
    dto.extension,
    dto.fileName
) {
    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumns(
        JoinColumn(name = "student_id"),
        JoinColumn(name = "notice_id")
    )
    @JsonIgnore
    var applicant: Applicant = applicant
        protected set


}