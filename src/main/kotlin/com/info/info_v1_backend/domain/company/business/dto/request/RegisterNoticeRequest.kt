package com.info.info_v1_backend.domain.company.business.dto.request

import com.info.info_v1_backend.domain.company.data.entity.notice.embeddable.*
import com.info.info_v1_backend.domain.company.data.entity.type.WorkTime
import java.time.LocalDate

data class RegisterNoticeRequest(
    val targetMajorList: MutableList<TargetMajor>,
    val businessInformation: String,
    val certificateList: MutableList<String>,
    val cutLine: Int?,
    val workRemark: String,
    val commuteTime: CommuteTime,
    val workTime: WorkTime,
    val fieldTrainingPay: Long,
    val employmentPay: EmploymentPay,
    val screeningProcedure: ScreeningProcedure,
    val alternativeMilitaryPlan: Boolean,
    val mealSupport: MealSupport,
    val welfare: Welfare,
    val needDocuments: String?,
    val deadLine: LocalDate?,
    val isAlwaysOpen: Boolean,
    val interviewHopeMonth: LocalDate?,
    val workHopeMonth: LocalDate?

)
