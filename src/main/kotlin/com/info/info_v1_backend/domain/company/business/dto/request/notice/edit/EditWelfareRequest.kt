package com.info.info_v1_backend.domain.company.business.dto.request.notice.edit

data class EditWelfareRequest(
    val dormitorySupport: Boolean?,
    val selfDevelopmentPay: Boolean?,
    val equipmentSupport: Boolean?,
    val youthTomorrowChaeumDeduction: Boolean?,
    val alternativeMilitaryPlan: Boolean?,
    var elseSupport: String?
)
