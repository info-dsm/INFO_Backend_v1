package com.info.info_v1_backend.domain.company.business.dto.response.notice

import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.*
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyResponse
import com.info.info_v1_backend.global.file.dto.FileResponse

data class MaximumNoticeWithoutPayResponse(
    val noticeId: Long,
    val company: MaximumCompanyResponse,
    val recruitmentBusinessResponse: List<RecruitmentBusinessResponse>,
    val workTime: WorkTimeRequest,
    val mealSupport: MealSupportRequest,
    val welfare: WelfareRequest,
    val noticeOpenPeriod: NoticeOpenPeriodRequest,
    val interviewProcessList: List<Map<Int, String>>,
    val needDocuments: String?,
    val otherFeatures: String?,
    val workPlace: WorkPlaceRequest,
    val formAttachmentList: List<FileResponse>,
    val applicantCount: Int

)
