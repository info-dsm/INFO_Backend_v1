package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.company.business.dto.request.RegisterNoticeRequest

interface NoticeService {

    fun registerNotice(request: RegisterNoticeRequest)
}