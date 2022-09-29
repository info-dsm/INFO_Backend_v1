package com.info.info_v1_backend.domain.company.data.entity.notice.language

import java.io.Serializable

class LanguageUsageIdClass(
    languageId: String,
    recruitmentBusinessId: Long
): Serializable {
    val language: String = languageId

    val recruitmentBusiness: Long = recruitmentBusinessId

}