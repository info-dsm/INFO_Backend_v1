package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.business.dto.response.MinimumStudent
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.notice.CloseNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.response.company.FieldTrainingResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.FieldTrainingStudentWithHiredResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.HiredStudentResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.ApplicantResponse
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.notice.applicant.Applicant
import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.time.Year

interface HireService {

    fun getApplierList(user: User, noticeId: Long, idx: Int, size: Int): List<ApplicantResponse>
    fun applyNotice(user: User, noticeId: Long, reporterList: List<MultipartFile>)
    fun cancelApply(user: User, noticeId: Long, studentId: Long)

    fun getFieldTrainingStudentList(user: User, companyId: Long): List<FieldTrainingResponse>
    fun makeFieldTrainingAndCloseNotice(user: User, request: CloseNoticeRequest, noticeId: Long)
    fun fireFieldTrainingStudent(user: User, studentId: Long, companyId: Long)

    fun getHiredStudentList(user: User, companyId: Long): List<HiredStudentResponse>
    fun hireStudent(user: User, studentId: Long, companyId: Long, startDate: LocalDate)
    fun fireStudent(user: User, studentId: Long, companyId: Long)

    fun getFieldTrainingStudentWithHiredListInThisYear(user: User, idx: Int, size: Int, year: Year): Page<FieldTrainingStudentWithHiredResponse>
}