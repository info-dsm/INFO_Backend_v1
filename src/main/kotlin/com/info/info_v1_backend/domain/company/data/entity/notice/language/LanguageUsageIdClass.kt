package com.info.info_v1_backend.domain.company.data.entity.notice.language

import java.io.Serializable

class LanguageUsageIdClass(
    languageId: String,
    noticeId: Long
): Serializable {
    val languageId: String = languageId

    val noticeId: Long = noticeId

}