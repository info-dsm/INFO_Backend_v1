package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.company.business.dto.request.notice.CloseNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.EditNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.RegisterNoticeRequest

interface NoticeService {

    fun registerNotice(request: RegisterNoticeRequest)
    fun editNotice(request: EditNoticeRequest)
    fun deleteNotice(noticeId: Long)
    fun closeNotice(request: CloseNoticeRequest)
    fun getMinimumNotice(idx: Int, size: Int)
    fun getMaximumNotice(id: Long)
    fun searchNotice(query: String)


}