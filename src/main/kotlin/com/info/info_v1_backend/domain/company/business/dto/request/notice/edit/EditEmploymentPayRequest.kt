package com.info.info_v1_backend.domain.company.business.dto.request.notice.edit

data class EditEmploymentPayRequest(
    val yearPayStart: Long?,
    val yearPayEnd: Long?,
    val bonus: Long?
)
