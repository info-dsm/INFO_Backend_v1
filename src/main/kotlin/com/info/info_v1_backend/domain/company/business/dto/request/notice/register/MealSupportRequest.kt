package com.info.info_v1_backend.domain.company.business.dto.request.notice.register

import javax.persistence.Column
import javax.validation.constraints.Min

data class MealSupportRequest(
    @field:Min(0, message = "mealSupportPay는 0 이사이어야합니다.")
    var mealSupportPay: Long,
    var breakfast: Boolean,
    var lunch: Boolean,
    var dinner: Boolean
)
