package com.info.info_v1_backend.domain.company.data.entity.notice.language

import java.io.Serializable

class LanguageUsageIdClass(
    languageId: String,
    recruitmentBusinessId: Long
): Serializable {
    val languageId: String = languageId

    val recruitmentBusinessId: Long = recruitmentBusinessId

}