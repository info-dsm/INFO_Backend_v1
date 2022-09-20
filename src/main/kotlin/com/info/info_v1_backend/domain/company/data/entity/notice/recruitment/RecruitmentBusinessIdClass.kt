package com.info.info_v1_backend.domain.company.data.entity.notice.recruitment

import java.io.Serializable


class RecruitmentBusinessIdClass(
    bigClassificationId: String,
    smallClassificationId: String
): Serializable {
    val bigClassificationId: String = bigClassificationId

    val smallClassificationId: String = smallClassificationId
}