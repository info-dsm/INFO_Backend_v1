package com.info.info_v1_backend.domain.company.business.dto.request.notice

import javax.validation.constraints.Size


data class ScreeningProcedureRequest(
    var document: Boolean,
    var technicalInterview: Boolean,
    var physicalCheck: Boolean,
    var assignment: Boolean,
    var executiveInterview: Boolean,
    @field:Size(min = 0, max = 255, message = "elseProcedure의 최대길이는 255입니다.")
    var elseProcedure: String?
)
