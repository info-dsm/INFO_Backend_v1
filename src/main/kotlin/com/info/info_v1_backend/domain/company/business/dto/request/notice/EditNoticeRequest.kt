package com.info.info_v1_backend.domain.company.business.dto.request.notice

import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.MealSupportRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.PayRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.WelfareRequest
import java.time.LocalDate
import javax.validation.constraints.Size

data class EditNoticeRequest(
    var targetMajorList: MutableList<TargetMajorRequest>,
    @field:Size(max = 255, message = "businessInformation 최대 길이는 255입니다.")
    var businessInformation: String?,
    var certificateList: MutableList<String>?,
    var cutLine: Int?,
    @field:Size(max = 255, message = "personalRemark 최대 길이는 255입니다.")
    var personalRemark: String?,
    var commuteTime: CommuteTimeRequest?,
    var workTimePerDay: WorkTimePerDay?,
    var screeningProcedure: ScreeningProcedureRequest?,
    var alternativeMilitaryPlan: Boolean?,
    var mealSupport: MealSupportRequest?,
    var welfare: WelfareRequest?,
    var needDocuments: String?,
    var deadLine: LocalDate?,
    var isAlwaysOpen: Boolean?,
    var interviewHopeMonth: LocalDate?,
    var workHopeMonth: LocalDate?,
    var pay: PayRequest?,

    )
