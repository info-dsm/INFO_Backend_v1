package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.notice.edit.EditNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.RegisterNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.response.notice.*
import com.info.info_v1_backend.domain.company.data.entity.notice.interview.InterviewProcess
import com.info.info_v1_backend.global.file.dto.FileResponse
import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile

interface NoticeService {

    fun registerNotice(user: User, request: RegisterNoticeRequest, attachmentList: List<MultipartFile>) : NoticeIdResponse
    fun editNotice(user: User, request: EditNoticeRequest, noticeId: Long)

    fun getClassificationList(): List<ClassificationResponse>
    fun changeClassification(user: User, bigClassificationName: String, smallClassificationName: String, noticeId: Long, recruitmentBusinessId: Long)

    fun getLanguageList(): List<LanguageResponse>
    fun addLanguageSet(user: User, languageName: String, noticeId: Long, recruitmentBusinessId: Long)
    fun removeLanguageSet(user: User, languageName: String, noticeId: Long, recruitmentBusinessId: Long)

    fun getTechnologyList(): List<TechnologyResponse>
    fun addTechnologySet(user: User, technologyName: String, noticeId: Long, recruitmentBusinessId: Long)
    fun removeTechnologySet(user: User, technologyName: String, noticeId: Long, recruitmentBusinessId: Long)

    fun changeAttachment(user: User, attachmentList: List<MultipartFile>, noticeId: Long)
    fun changeInterviewProcess(user: User, interviewProcessMap: Map<Int, InterviewProcess>, noticeId: Long)
    fun removeInterviewProcess(user: User, interviewSequence: Int, noticeId: Long)

    fun getCertificateList(): List<CertificateResponse>
    fun addCertificate(user: User, certificateName: String, noticeId: Long, recruitmentBusinessId: Long)
    fun removeCertificate(user: User, certificateName: String, noticeId: Long, recruitmentBusinessId: Long)

    fun deleteNotice(user: User, noticeId: Long)

    fun approveNotice(user: User, noticeId: Long)
    fun rejectNotice(user: User, noticeId: Long)
    fun getWaitingNoticeList(user: User, idx: Int, size: Int): Page<MinimumNoticeResponse>

    fun getMyNoticeList(user: User): List<NoticeWithApproveStatusResponse>
    fun getMinimumNoticeList(idx: Int, size: Int): Page<MinimumNoticeResponse>
    fun getMaximumNotice(id: Long, user: User): MaximumNoticeWithoutPayResponse
    fun searchMinimumNoticeList(query: String): Page<MinimumNoticeResponse>?

    fun printNotice(user: User, noticeId: Long): FileResponse

}