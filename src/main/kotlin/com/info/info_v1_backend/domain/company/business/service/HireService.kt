package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.business.dto.response.MinimumStudent
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.notice.CloseNoticeRequest
import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

interface HireService {

    fun applyNotice(user: User, noticeId: Long, reporterList: List<MultipartFile>)
    fun getApplierList(user: User, noticeId: Long, idx: Int, size: Int): Page<MinimumStudent>
    fun cancelApply(user: User, noticeId: Long, studentId: Long)

    fun makeFieldTrainingAndCloseNotice(user: User, request: CloseNoticeRequest, noticeId: Long)
    fun fireFieldTrainingStudent(user: User, studentId: Long, companyId: Long)

    fun hireStudent(user: User, studentId: Long, companyId: Long, startDate: LocalDate)
    fun fireStudent(user: User, studentId: Long, companyId: Long)
}