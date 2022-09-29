package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.business.dto.response.MinimumStudent
import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.Teacher
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.data.repository.user.StudentRepository
import com.info.info_v1_backend.domain.auth.exception.IsNotContactorOrTeacher
import com.info.info_v1_backend.domain.auth.exception.IsNotStudentException
import com.info.info_v1_backend.domain.company.business.dto.request.notice.CloseNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.edit.EditNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.AddRecruitmentRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.RegisterNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MaximumNoticeWithoutPayResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MinimumNoticeResponse
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.company.work.field.FieldTraining
import com.info.info_v1_backend.domain.company.data.entity.notice.file.FormAttachment
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.notice.applicant.Applicant
import com.info.info_v1_backend.domain.company.data.entity.notice.certificate.Certificate
import com.info.info_v1_backend.domain.company.data.entity.notice.classification.RecruitmentBigClassification
import com.info.info_v1_backend.domain.company.data.entity.notice.classification.RecruitmentSmallClassification
import com.info.info_v1_backend.domain.company.data.entity.notice.file.Reporter
import com.info.info_v1_backend.domain.company.data.entity.notice.interview.InterviewProcess
import com.info.info_v1_backend.domain.company.data.entity.notice.interview.InterviewProcessUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.recruitment.RecruitmentBusiness
import com.info.info_v1_backend.domain.company.data.repository.company.*
import com.info.info_v1_backend.domain.company.data.repository.notice.*
import com.info.info_v1_backend.domain.company.exception.CompanyNotFoundException
import com.info.info_v1_backend.domain.company.exception.NoticeNotFoundException
import com.info.info_v1_backend.global.error.common.NoAuthenticationException
import com.info.info_v1_backend.global.file.repository.FileRepository
import com.info.info_v1_backend.infra.amazon.s3.S3Util
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.query.TextCriteria
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class NoticeServiceImpl(
    private val noticeRepository: NoticeRepository,
    private val recruitmentBusinessRepository: RecruitmentBusinessRepository,
    private val bigClassificationRepository: RecruitmentBigClassificationRepository,
    private val smallClassificationRepository: RecruitmentSmallClassificationRepository,
    private val studentRepository: StudentRepository,
    private val noticeSearchDocumentRepository: NoticeSearchDocumentRepository,
    private val companyRepository: CompanyRepository,
    private val s3Util: S3Util,
    private val applicantRepository: ApplicantRepository,
    private val hiredStudentRepository: HiredStudentRepository,
    private val fieldTrainingRepository: FieldTrainingRepository,
    private val formAttachmentRepository: FileRepository<FormAttachment>,
    private val certificateRepository: CertificateRepository,
    private val reporterFileRepository: ReporterFileRepository
): NoticeService {

    override fun registerNotice(user: User, request: RegisterNoticeRequest, attachmentList: List<MultipartFile>) {
        if (user is Company) {

            val notice = noticeRepository.save(
                Notice(
                    user,
                    request.workTime.toWorkTime(),
                    request.pay.toPay(),
                    request.mealSupport.toMealSupport(),
                    request.welfare.toWelfare(),
                    request.noticeOpenPeriod.toNoticeOpenPeriod(),
                    request.interviewProcessMap.map {
                        InterviewProcessUsage(
                            it.key,
                            it.value
                        )
                    },
                    request.needDocuments,
                    request.otherFeatures,
                    request.workPlace.toWorkPlace(),
                    request.isPersonalContact
                )
            )

            val bigClassification = bigClassificationRepository.findByIdOrNull(request.recruitmentRequest.bigClassification)?: let {
                bigClassificationRepository.save(
                    RecruitmentBigClassification(
                        request.recruitmentRequest.bigClassification
                    )
                )
            }
            notice.addRecruitmentBusiness(
                recruitmentBusinessRepository.save(
                    RecruitmentBusiness(
                        bigClassification,
                        smallClassificationRepository.findByIdOrNull(request.recruitmentRequest.smallClassification)?: let {
                            smallClassificationRepository.save(
                                RecruitmentSmallClassification(
                                    request.recruitmentRequest.smallClassification,
                                    bigClassification
                                )
                            )
                        },
                        request.recruitmentRequest.numberOfEmployee,
                        notice,
                        request.recruitmentRequest.detailBusinessDescription,
                        request.recruitmentRequest.gradeCutLine
                    )
                )
            )


            attachmentList.map {
                notice.addAttachment(
                    FormAttachment(
                        s3Util.uploadFile(it, "notice/${notice.id}", "attachment"),
                        notice
                    )
                )
            }

            request.recruitmentRequest.needCertificateList.map {
                certificateRepository.save(
                    certificateRepository.findByIdOrNull(it)
                        ?: Certificate(
                            it,
                            notice.recruitmentBusiness!!
                        )
                )
            }

        } else throw IsNotContactorOrTeacher(user.roleList.toString())
    }

    override fun changeAttachment(user: User, attachmentList: List<MultipartFile>, noticeId: Long) {
        if (user is Company) {
            val notice = user.noticeList.first {
                it.id == noticeId
            }

            attachmentList.map {
                formAttachmentRepository.save(
                    FormAttachment(
                        s3Util.uploadFile(it, "notice/${noticeId}", "attachment"),
                        notice
                    )
                )
            }

        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun changeInterviewProcess(user: User, interviewProcessMap: Map<Int, InterviewProcess>, noticeId: Long) {
        if (user is Company) {
            val notice = user.noticeList.first {
                it.id!! == noticeId
            }

            interviewProcessMap.map {
                notice.changeInterviewProcess(
                    it.key,
                    it.value
                )
            }

        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun editNotice(user: User, request: EditNoticeRequest, noticeId: Long) {
        if (user is Company) {
            val notice = noticeRepository.findByIdAndCompany(noticeId, user).orElse(null)?: throw NoticeNotFoundException(noticeId.toString())
            notice.editNotice(request)

        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun deleteNotice(user: User, noticeId: Long) {
        val notice = noticeRepository.findById(noticeId).orElse(null)?: throw NoticeNotFoundException(noticeId.toString())
        if (!checkAuthentication(user, notice)) throw NoAuthenticationException(user.roleList.toString())
        noticeRepository.delete(notice)
    }

    override fun approveNotice(user: User, noticeId: Long) {
        if (user is Teacher) {
            val notice = noticeRepository.findByIdOrNull(noticeId)?: throw NoticeNotFoundException(noticeId.toString())
            notice.approveNotice()
        } else throw NoAuthenticationException(noticeId.toString())
    }

    override fun rejectNotice(user: User, noticeId: Long) {
        if (user is Teacher) {
            noticeRepository.delete(
                noticeRepository.findByIdOrNull(noticeId)?: throw NoticeNotFoundException(noticeId.toString())
            )
        } else throw NoAuthenticationException(noticeId.toString())
    }

    override fun getWaitingNoticeList(user: User, idx: Int, size: Int): Page<MinimumNoticeResponse> {
        if (user is Teacher) {
            return noticeRepository.findAllByApprove(false, PageRequest.of(idx, size, Sort.by("created_at").descending())).map {
                it.toMinimumNoticeResponse()
            }
        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun applyNotice(user: User, noticeId: Long, reporterList: List<MultipartFile>) {
        if (user is Student) {
            val notice = noticeRepository.findByIdOrNull(noticeId)?: throw NoticeNotFoundException(noticeId.toString())
            val applicant = applicantRepository.save(
                Applicant(
                    user,
                    notice,
                )
            )

            reporterList.map {
                reporterFileRepository.save(
                    Reporter(
                        s3Util.uploadFile(it, "notice/${notice.id!!}", "reporter"),
                        applicant
                    )
                )
            }

        } else throw IsNotStudentException(user.roleList.toString())
    }

    override fun getApplierList(user: User, noticeId: Long): List<MinimumStudent> {
        if (user is Company || user is Teacher) {
            val notice = noticeRepository.findByIdOrNull(noticeId)?: throw NoticeNotFoundException(noticeId.toString())
            return notice.applicantList.map {
                it.student.toMinimumStudent()
            }
        } else throw NoAuthenticationException(noticeId.toString())
    }

    override fun makeFieldTrainingAndCloseNotice(user: User, request: CloseNoticeRequest, noticeId: Long) {
        if (user is Company || user is Teacher) {
            val notice = noticeRepository.findByIdOrNull(noticeId) ?: throw NoticeNotFoundException(noticeId.toString())

            notice.applicantList.filter {
                request.studentIdList.contains(
                    it.student.id!!
                )
            }.map { applicant: Applicant ->
                fieldTrainingRepository.save(
                    FieldTraining(
                        applicant.student,
                        notice.company,
                        request.fieldTrainingStartDate,
                        request.fieldTrainingEndDate
                    )
                )
            }
        } else throw NoAuthenticationException(user.roleList.toString())
    }

    private fun checkAuthentication(current: User, notice: Notice): Boolean {
        return (current is Company && current.noticeList.contains(notice))
    }

    override fun getMinimumNoticeList(idx: Int, size: Int, isApprove: Boolean): Page<MinimumNoticeResponse> {
        if (isApprove) return noticeRepository.findAll(PageRequest.of(idx, size, Sort.by("created_at").descending())).map {
            it.toMinimumNoticeResponse()
        }
        return noticeRepository.findAllByApprove(true, PageRequest.of(idx, size, Sort.by("created_at").descending())).map {
            it.toMinimumNoticeResponse()
        }
    }

    override fun getMaximumNotice(id: Long): MaximumNoticeWithoutPayResponse {
        return (noticeRepository.findByIdOrNull(id)?: throw NoticeNotFoundException(id.toString())).toMaximumNoticeResponse()
    }

    override fun searchMinimumNoticeList(query: String): Page<MinimumNoticeResponse> {
        val criteria = TextCriteria()
        criteria.matchingAny(query)
        
        return noticeSearchDocumentRepository.findAllBy(criteria, PageRequest.of(0, 20)).map {
                noticeSearchDocument ->
            noticeRepository.findById(noticeSearchDocument.noticeId).orElse(null)?.let {
                (it.toMinimumNoticeResponse())
            }
        }
    }

    override fun printNotice(user: User, noticeId: Long) {
        TODO("Not yet implemented")
    }

    override fun searchCertificate() {
        TODO("Not yet implemented")
    }

    override fun searchBigClassification() {
        TODO("Not yet implemented")
    }

    override fun searchSmallClassification() {
        TODO("Not yet implemented")
    }
}