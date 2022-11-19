package com.info.info_v1_backend.domain.company.data.entity.notice.technology

import java.io.Serializable

data class TechnologyUsageIdClass(
    var technology: String? = null,
    var recruitmentBusiness: Long? = null
): Serializable