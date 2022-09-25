package com.info.info_v1_backend.domain.company.data.entity.notice.technology

import java.io.Serializable

class TechnologyUsageIdClass(
    technologyId: String,
    recruitmentBusinessId: Long
): Serializable {
    val technologyId: String = technologyId

    val recruitmentBusinessId: Long = recruitmentBusinessId
}