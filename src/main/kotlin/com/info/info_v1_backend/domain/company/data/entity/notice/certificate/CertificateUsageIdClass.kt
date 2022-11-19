package com.info.info_v1_backend.domain.company.data.entity.notice.certificate

import java.io.Serializable


data class CertificateUsageIdClass(
    var certificate: String? = null,
    var recruitmentBusiness: Long? = null
): Serializable