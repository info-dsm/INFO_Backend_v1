package com.info.info_v1_backend.domain.company.data.entity.notice.certificate

import java.io.Serializable


class CertificateUsageIdClass(
    certificateId: String,
    noticeId: Long
): Serializable {

    val certificate: String = certificateId

    val notice: Long = noticeId

}