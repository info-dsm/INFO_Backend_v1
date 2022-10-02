package com.info.info_v1_backend.domain.company.business.dto.response.notice

data class NoticeWithIsApproveResponse(
    val notice: MaximumNoticeWithPayResponse,
    val isApprove: Boolean

)
