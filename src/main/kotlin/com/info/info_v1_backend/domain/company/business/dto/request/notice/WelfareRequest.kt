package com.info.info_v1_backend.domain.company.business.dto.request.notice

import javax.validation.constraints.Size

data class WelfareRequest(
    var dormitorySupport: Boolean,
    var selfDevelopmentPay: Boolean,
    var equipmentSupport: Boolean,
    @field:Size(max = 255, message = "elseSupport의 최대길이는 255입니다.")
    var elseSupport: String?
)
