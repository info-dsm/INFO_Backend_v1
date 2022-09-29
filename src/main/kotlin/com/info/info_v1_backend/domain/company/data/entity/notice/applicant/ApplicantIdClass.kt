package com.info.info_v1_backend.domain.company.data.entity.notice.applicant

class ApplicantIdClass(
    noticeId: Long,
    studentId: Long
): java.io.Serializable {
    val notice: Long = noticeId

    val student: Long = studentId
}