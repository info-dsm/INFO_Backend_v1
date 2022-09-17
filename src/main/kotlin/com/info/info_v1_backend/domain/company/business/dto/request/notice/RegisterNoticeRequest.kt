package com.info.info_v1_backend.domain.company.business.dto.request.notice

import com.info.info_v1_backend.domain.company.data.entity.notice.embeddable.*
import com.info.info_v1_backend.domain.company.data.entity.type.WorkTime
import java.time.LocalDate
import javax.validation.constraints.Min
import javax.validation.constraints.Size

data class RegisterNoticeRequest(
    val targetMajorList: MutableList<TargetMajorRequest>,
    @field:Size(max = 255, message = "businessInformation 최대 길이는 255입니다.")
    val businessInformation: String,
    val certificateList: MutableList<String>,
    val cutLine: Int?,
    @field:Size(max = 255, message = "personalRemark 최대 길이는 255입니다.")
    val personalRemark: String,
    val commuteTime: CommuteTimeRequest,
    val workTime: WorkTime,
    @field:Min(0, message = "fieldTrainingPay는 최소 0입니다.")
    val fieldTrainingPay: Long,
    val employmentPay: EmploymentPay,
    val screeningProcedure: ScreeningProcedureRequest,
    val alternativeMilitaryPlan: Boolean,
    val mealSupport: MealSupportRequest,
    val welfare: WelfareRequest,
    val needDocuments: String?,
    val deadLine: LocalDate?,
    val isAlwaysOpen: Boolean,
    val interviewHopeMonth: LocalDate?,
    val workHopeMonth: LocalDate?

)
