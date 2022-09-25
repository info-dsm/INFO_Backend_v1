package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.data.repository.user.StudentRepository
import com.info.info_v1_backend.domain.auth.exception.IsNotContactorOrTeacher
import com.info.info_v1_backend.domain.company.business.dto.request.notice.CloseNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.EditNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.AddRecruitmentRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.RegisterNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MaximumNoticeResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MinimumNoticeResponse
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.notice.Attachment
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.notice.classification.RecruitmentBigClassification
import com.info.info_v1_backend.domain.company.data.entity.notice.classification.RecruitmentSmallClassification
import com.info.info_v1_backend.domain.company.data.entity.notice.embeddable.*
import com.info.info_v1_backend.domain.company.data.entity.notice.recruitment.RecruitmentBusiness
import com.info.info_v1_backend.domain.company.data.repository.company.CompanyRepository
import com.info.info_v1_backend.domain.company.data.repository.notice.*
import com.info.info_v1_backend.domain.company.exception.NoticeNotFoundException
import com.info.info_v1_backend.global.error.common.NoAuthenticationException
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
    private val s3Util: S3Util
): NoticeService {

    override fun registerNotice(company: User, request: RegisterNoticeRequest, attachmentList: List<MultipartFile>) {
        if (company is Company) {

            val notice = noticeRepository.save(
                Notice(
                    company,
                    request.workTime.toWorkTime(),
                    request.pay.toPay(),
                    request.mealSupport.toMealSupport(),
                    request.welfare.toWelfare(),
                    request.noticeOpenPeriod.toNoticeOpenPeriod(),
                    request.interviewProcessMap,
                    request.needDocuments,
                    request.otherFeatures,
                    request.workPlace.toWorkPlace(),
                    request.isPersonalContact
                )
            )

            request.recruitmentRequestList.map {
                r: AddRecruitmentRequest ->
                recruitmentBusinessRepository.save(
                    RecruitmentBusiness(
                        bigClassificationRepository.findByIdOrNull(r.bigClassification)?: let {
                            bigClassificationRepository.save(
                                RecruitmentBigClassification(
                                    r.bigClassification
                                )
                            )
                        },
                        smallClassificationRepository.findByIdOrNull(request.smallClassification)?: let {
                            smallClassificationRepository.save(
                                RecruitmentSmallClassification(
                                    r.smallClassification
                                )
                            )
                        },
                        r.numberOfEmployee,
                        notice,
                        r.detailBusinessDescription,
                        r.gradeCutLine
                    )
                )
            }

            attachmentList.map {
                val file = s3Util.uploadFile(it, "notice/${notice.id}", "attachment")

                notice.addAttachment(
                    Attachment(
                        notice,
                        file.fileUrl,
                        file.fileType,
                        file.extension
                    )
                )
            }
            
        } else throw IsNotContactorOrTeacher(company.roleList.toString())
    }

    override fun editNotice(user: User, request: EditNoticeRequest, noticeId: Long) {

    }

    override fun deleteNotice(user: User, noticeId: Long) {
        val current = currentUtil.getCurrentUser()
        val notice = noticeRepository.findById(noticeId).orElse(null)?: throw NoticeNotFoundException(noticeId.toString())
        if (checkAuthentication(current, notice)) throw NoAuthenticationException(current.roleList.toString())
        noticeRepository.delete(notice)
    }

    override fun closeNotice(user: User, request: CloseNoticeRequest, noticeId: Long) {


    }

    private fun checkAuthentication(current: User, notice: Notice): Boolean {
        return ((current is Company) && (current.noticeList.contains(notice) == true))
    }

    override fun getMinimumNoticeList(idx: Int, size: Int, isExpired: Boolean): Page<MinimumNoticeResponse> {
        if (isExpired) return noticeRepository.findAll(PageRequest.of(idx, size, Sort.by("created_at").descending())).map {
            it.toMinimumNoticeResponse()
        }
        return noticeRepository.findAllByExpiredIsNot(false, PageRequest.of(idx, size, Sort.by("created_at").descending())).map {
            it.toMinimumNoticeResponse()
        }
    }

    override fun getMaximumNotice(id: Long): MaximumNoticeResponse {
        return (noticeRepository.findById(id).orElse(null)?: throw NoticeNotFoundException(id.toString())).toMaximumNoticeResponse()
    }

    override fun searchMinimumNoticeList(query: String): List<MinimumNoticeResponse> {
        val criteria = TextCriteria()
        criteria.matchingAny(query)

        val minimumNoticeResponseList: MutableList<MinimumNoticeResponse> = ArrayList()
        noticeSearchDocumentRepository.findAllBy(criteria, PageRequest.of(0, 20)).map {
        noticeSearchDocument ->
            noticeRepository.findById(noticeSearchDocument.noticeId).orElse(null)?.let{
                minimumNoticeResponseList.add(it.toMinimumNoticeResponse())
            }
        }
        
        return minimumNoticeResponseList
    }
}