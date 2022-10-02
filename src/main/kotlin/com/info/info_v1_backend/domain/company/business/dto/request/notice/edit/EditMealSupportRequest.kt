package com.info.info_v1_backend.domain.company.business.dto.request.notice.edit

data class EditMealSupportRequest(
    val mealSupportPay: Long?,
    val breakfast: Boolean?,
    val lunch: Boolean?,
    val dinner: Boolean?

)
