package com.info.info_v1_backend.domain.company.business.dto.request.notice.register

import com.info.info_v1_backend.domain.company.data.entity.notice.interview.InterviewProcess

data class RegisterNoticeRequest(
    val recruitmentRequest: AddRecruitmentRequest,
    val workTime: WorkTimeRequest,
    val pay: PayRequest,
    val mealSupport: MealSupportRequest,
    val welfare: WelfareRequest,
    val noticeOpenPeriod: NoticeOpenPeriodRequest,
    val interviewProcessMap: Map<Int, InterviewProcess>,
    val needDocuments: String?,
    val otherFeatures: String?,
    val workPlace: WorkPlaceRequest,
    val isPersonalContact: Boolean,

)
