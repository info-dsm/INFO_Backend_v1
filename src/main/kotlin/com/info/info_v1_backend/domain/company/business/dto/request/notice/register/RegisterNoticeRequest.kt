package com.info.info_v1_backend.domain.company.business.dto.request.notice.register

import com.info.info_v1_backend.domain.company.data.entity.notice.interview.InterviewProcess
import org.springframework.validation.annotation.Validated
import javax.validation.Valid

@Validated
data class RegisterNoticeRequest(
    val recruitmentRequest: AddRecruitmentRequest,
    @field:Valid
    val workTime: WorkTimeRequest,
    val pay: PayRequest,
    @field:Valid
    val mealSupport: MealSupportRequest,
    @field:Valid
    val welfare: WelfareRequest,
    val noticeOpenPeriod: NoticeOpenPeriodRequest,
    val interviewProcessMap: Map<Int, InterviewProcess>,
    val needDocuments: String?,
    val otherFeatures: String?,
    val workPlace: WorkPlaceRequest,
    val isPersonalContact: Boolean,

)
