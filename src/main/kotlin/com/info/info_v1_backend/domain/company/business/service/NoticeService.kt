package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.business.dto.response.MinimumStudent
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.notice.CloseNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.edit.EditNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.RegisterNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.response.notice.*
import com.info.info_v1_backend.domain.company.data.entity.notice.interview.InterviewProcess
import com.info.info_v1_backend.global.file.dto.FileDto
import com.info.info_v1_backend.global.file.dto.FileResponse
import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile

interface NoticeService {

    fun registerNotice(user: User, request: RegisterNoticeRequest, attachmentList: List<MultipartFile>)
    fun editNotice(user: User, request: EditNoticeRequest, noticeId: Long)
    fun deleteNotice(user: User, noticeId: Long)

    fun changeAttachment(user: User, attachmentList: List<MultipartFile>, noticeId: Long)
    fun changeInterviewProcess(user: User, interviewProcessMap: Map<Int, InterviewProcess>, noticeId: Long)

    fun approveNotice(user: User, noticeId: Long)
    fun rejectNotice(user: User, noticeId: Long)
    fun getWaitingNoticeList(user: User, idx: Int, size: Int): Page<MinimumNoticeResponse>

    fun getMinimumNoticeList(idx: Int, size: Int, isApprove: Boolean): Page<MinimumNoticeResponse>
    fun getMaximumNotice(id: Long): MaximumNoticeWithoutPayResponse
    fun searchMinimumNoticeList(query: String): Page<MinimumNoticeResponse>

    fun printNotice(user: User, noticeId: Long): FileResponse

    fun searchCertificate(query: String): Page<CertificateResponse>
    fun searchBigClassification(query: String): List<BigClassificationResponse>
    fun searchSmallClassification(query: String): List<SmallClassificationResponse>

}