package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.company.business.dto.request.notice.CloseNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.EditNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.RegisterNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MaximumNoticeResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MinimumNoticeResponse
import org.springframework.data.domain.Page

interface NoticeService {

    fun registerNotice(request: RegisterNoticeRequest)
    fun editNotice(request: EditNoticeRequest, noticeId: Long)
    fun deleteNotice(noticeId: Long)
    fun closeNotice(request: CloseNoticeRequest, noticeId: Long)
    fun getMinimumNoticeList(idx: Int, size: Int, isExpired: Boolean): Page<MinimumNoticeResponse>
    fun getMaximumNotice(id: Long): MaximumNoticeResponse
    fun searchMinimumNoticeList(query: String): List<MinimumNoticeResponse>


}