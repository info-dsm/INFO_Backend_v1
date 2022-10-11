package com.info.info_v1_backend.domain.company.business.dto.response.notice

import com.info.info_v1_backend.domain.company.data.entity.notice.NoticeWaitingStatus

data class NoticeWithIsApproveResponse(
    val notice: MaximumNoticeWithPayResponse,
    val isApprove: NoticeWaitingStatus

)
