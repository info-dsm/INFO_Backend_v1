package com.info.info_v1_backend.domain.company.business.dto.request.notice.register

import javax.validation.constraints.Min

data class EmploymentPayRequest(
    @field:Min(0, message = "yearPayStart는 0 이상이어야합니다.")
    val yearPayStart: Long,
    @field:Min(0, message = "yearPayEnd는 0 이상이어야합니다.")
    val yearPayEnd: Long,
    @field:Min(0, message = "bonus는 0 이상이어야합니다.")
    val bonus: Long?
)
