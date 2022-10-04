package com.info.info_v1_backend.domain.company.business.dto.request.notice.edit

import com.info.info_v1_backend.domain.company.business.dto.response.notice.RecruitmentBusinessResponse


data class EditNoticeRequest(
    val recruitmentBusiness: EditRecruitmentRequest?,
    val workTime: EditWorkTimeRequest?,
    val pay: EditPayRequest?,
    val mealSupport: EditMealSupportRequest?,
    val welfare: EditWelfareRequest?,
    val noticeOpenPeriod: EditNoticeOpenPeriodRequest?,
    val needDocuments: String?,
    val otherFeatures: String?,
    val workPlace: EditWorkPlaceRequest?,
    val isPersonalContact: Boolean?

)
