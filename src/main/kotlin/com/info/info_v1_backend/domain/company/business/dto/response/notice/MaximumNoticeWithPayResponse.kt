package com.info.info_v1_backend.domain.company.business.dto.response.notice

import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.*
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyResponse
import com.info.info_v1_backend.domain.company.data.entity.notice.interview.InterviewProcess
import com.info.info_v1_backend.global.file.dto.FileResponse

data class MaximumNoticeWithPayResponse(
    val noticeId: Long,
    val company: MaximumCompanyResponse,
    val recruitmentBusinessResponse: RecruitmentBusinessResponse,
    val pay: PayRequest,
    val workTime: WorkTimeRequest,

    val mealSupport: MealSupportRequest,

    val welfare: WelfareRequest,
    val noticeOpenPeriod: NoticeOpenPeriodRequest,
    val interviewProcessList: List<InterviewProcess>,
    val needDocuments: String?,
    val otherFeatures: String?,
    val workPlace: WorkPlaceRequest,
    val formAttachmentList: List<FileResponse>,
    val applicantCount: Int


)
