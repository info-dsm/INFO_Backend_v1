package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.notice.CloseNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.EditNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.RegisterNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MaximumNoticeResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MinimumNoticeResponse
import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile

interface NoticeService {

    fun registerNotice(user: User, request: RegisterNoticeRequest, attachmentList: List<MultipartFile>)
    fun editNotice(user: User, request: EditNoticeRequest, noticeId: Long)
    fun deleteNotice(user: User, noticeId: Long)
    fun closeNotice(user: User, request: CloseNoticeRequest, noticeId: Long)
    fun getMinimumNoticeList(idx: Int, size: Int, isExpired: Boolean): Page<MinimumNoticeResponse>
    fun getMaximumNotice(id: Long): MaximumNoticeResponse
    fun searchMinimumNoticeList(query: String): List<MinimumNoticeResponse>


}