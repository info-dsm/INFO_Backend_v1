package com.info.info_v1_backend.domain.company.business.dto.response.notice

import com.info.info_v1_backend.domain.company.business.dto.request.notice.*
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse
import com.info.info_v1_backend.domain.company.data.entity.type.WorkTime
import java.time.LocalDate

data class MaximumNoticeResponse(
    val noticeId: Long,
    val company: MaximumCompanyResponse,
    val targetMajorList: List<TargetMajorRequest>,
    val deadLine: LocalDate?,
    val businessInformation: String,
    val certificateList: List<String>?,
    val cutLine: Int?,
    val personalRemark: String,
    val commuteTime: CommuteTimeRequest,
    val workTime: WorkTime,
    val pay: PayRequest?,
    val procedureRequest: ScreeningProcedureRequest,
    val alternativeMilitaryPlan: Boolean,
    val mealSupportRequest: MealSupportRequest,
    val welfareRequest: WelfareRequest,
    val needDocument: String?

)
