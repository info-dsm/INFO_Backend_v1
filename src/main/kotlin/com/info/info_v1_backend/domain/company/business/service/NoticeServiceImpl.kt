package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.Teacher
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.notice.edit.EditNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.RegisterNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.response.notice.*
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.notice.file.FormAttachment
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.notice.NoticeWaitingStatus
import com.info.info_v1_backend.domain.company.data.entity.notice.certificate.Certificate
import com.info.info_v1_backend.domain.company.data.entity.notice.certificate.CertificateSearchDocument
import com.info.info_v1_backend.domain.company.data.entity.notice.certificate.CertificateUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.certificate.CertificateUsageIdClass
import com.info.info_v1_backend.domain.company.data.entity.notice.classification.RecruitmentBigClassification
import com.info.info_v1_backend.domain.company.data.entity.notice.classification.RecruitmentSmallClassification
import com.info.info_v1_backend.domain.company.data.entity.notice.interview.InterviewProcess
import com.info.info_v1_backend.domain.company.data.entity.notice.interview.InterviewProcessUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.language.Language
import com.info.info_v1_backend.domain.company.data.entity.notice.language.LanguageUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.language.LanguageUsageIdClass
import com.info.info_v1_backend.domain.company.data.entity.notice.recruitment.RecruitmentBusiness
import com.info.info_v1_backend.domain.company.data.entity.notice.technology.Technology
import com.info.info_v1_backend.domain.company.data.entity.notice.technology.TechnologyUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.technology.TechnologyUsageIdClass
import com.info.info_v1_backend.domain.company.data.repository.company.*
import com.info.info_v1_backend.domain.company.data.repository.notice.*
import com.info.info_v1_backend.domain.company.exception.NoticeNotFoundException
import com.info.info_v1_backend.global.error.common.NoAuthenticationException
import com.info.info_v1_backend.global.file.dto.FileResponse
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
    private val noticeSearchDocumentRepository: NoticeSearchDocumentRepository,
    private val s3Util: S3Util,
    private val formAttachmentRepository: FileRepository<FormAttachment>,
    private val certificateRepository: CertificateRepository,
    private val languageUsageRepository: LanguageUsageRepository,
    private val languageRepository: LanguageRepository,
    private val technologyRepository: TechnologyRepository,
    private val technologyUsageRepository: TechnologyUsageRepository,
    private val certificateUsageRepository: CertificateUsageRepository
): NoticeService {

    override fun getBigClassificationList(): List<BigClassificationResponse> {
        return bigClassificationRepository.findAll().map {
            it.toBigClassificationResponse()
        }
    }

    override fun getSmallClassificationList(): List<SmallClassificationResponse> {
        return smallClassificationRepository.findAll().map {
            it.toSmallClassification()
        }
    }

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
                        )
                )
            }

        } else throw NoAuthenticationException(user.roleList.toString())
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

    override fun getCertificateList(): List<CertificateResponse> {
        return certificateRepository.findAll().map {
            it.toCertificateResponse()
        }
    }

    override fun addCertificate(user: User, certificateName: String, noticeId: Long) {
        if (user is Company) {
            val notice = noticeRepository.findByIdOrNull(noticeId)?: throw NoticeNotFoundException(noticeId.toString())
            certificateUsageRepository.save(
                CertificateUsage(
                    certificateRepository.findByIdOrNull(certificateName)?:
                    certificateRepository.save(
                        Certificate(
                            certificateName
                        )
                    ),
                    notice.recruitmentBusiness!!
                )
            )

        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun removeCertificate(user: User, certificateName: String, noticeId: Long) {
        if (user is Company) {
            val notice = noticeRepository.findByIdOrNull(noticeId)?: throw NoticeNotFoundException(noticeId.toString())

            certificateUsageRepository.delete(
                certificateUsageRepository.findByIdOrNull(
                    CertificateUsageIdClass(
                        "",
                        notice.recruitmentBusiness!!.id!!
                    )
                )?: return
            )

        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun editNotice(user: User, request: EditNoticeRequest, noticeId: Long) {
        if (user is Company) {
            val notice = noticeRepository.findByIdAndCompanyAndIsApproveNot(noticeId, user, NoticeWaitingStatus.REJECT).orElse(null)?: throw NoticeNotFoundException(noticeId.toString())
            notice.editNotice(request)

        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun changeClassification(user: User, bigClassificationName: String, smallClassificationName: String, noticeId: Long) {
        if (user is Company) {
            val bigClassification = bigClassificationRepository.findByIdOrNull(bigClassificationName)?:
            bigClassificationRepository.save(
                RecruitmentBigClassification(
                    bigClassificationName
                )
            )
            val smallClassification = smallClassificationRepository.findByNameAndAndBigClassification(
                smallClassificationName,
                bigClassification
            ).orElse(null)?: smallClassificationRepository.save(
                RecruitmentSmallClassification(
                    smallClassificationName,
                    bigClassification
                )
            )
            val notice = noticeRepository.findByIdAndCompanyAndIsApproveNot(noticeId, user, NoticeWaitingStatus.REJECT)
                .orElse(null)?: throw NoticeNotFoundException(noticeId.toString())
            notice.recruitmentBusiness!!.changeSmallClassification(smallClassification)
        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun getLanguageList(): List<LanguageResponse> {
        return languageRepository.findAll().map {
            it.toLanguageResponse()
        }
    }

    override fun addLanguageSet(user: User, languageName: String, noticeId: Long) {
        if (user is Company) {
            val notice = noticeRepository.findByIdAndCompanyAndIsApproveNot(noticeId, user, NoticeWaitingStatus.REJECT)
                .orElse(null)?: throw NoticeNotFoundException(noticeId.toString())
            notice.recruitmentBusiness!!.languageUsageSet.add(
                languageUsageRepository.findByLanguageAndRecruitmentBusiness(
                    languageRepository.findByIdOrNull(languageName)?: languageRepository.save(
                        Language(
                            languageName
                        )
                    ),
                    notice.recruitmentBusiness!!
                ).orElse(null)?: languageUsageRepository.save(
                    LanguageUsage(
                        languageRepository.findByIdOrNull(languageName)?: languageRepository.save(
                            Language(
                                languageName
                            )
                        ),
                        notice.recruitmentBusiness!!
                    )
                )
            )

        }

    }

    override fun removeLanguageSet(user: User, languageName: String, noticeId: Long) {
        if (user is Company) {
            languageUsageRepository.delete(
                languageUsageRepository.findByIdOrNull(
                    LanguageUsageIdClass(
                        languageName,
                        (noticeRepository.findByIdAndCompanyAndIsApproveNot(noticeId, user, NoticeWaitingStatus.REJECT)
                            .orElse(null)?: return).recruitmentBusiness!!.id!!
                    )
                )?: return
            )

        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun getTechnologyList(): List<TechnologyResponse> {
        return technologyRepository.findAll().map {
            it.toTechnologyResponse()
        }
    }

    override fun addTechnologySet(user: User, technologyName: String, noticeId: Long) {
        if (user is Company) {
            technologyUsageRepository.save(
                TechnologyUsage(
                    technologyRepository.findByIdOrNull(
                        technologyName
                    ) ?: technologyRepository.save(
                        Technology(
                            technologyName
                        )
                    ),
                    (noticeRepository.findByIdAndCompanyAndIsApproveNot(noticeId, user, NoticeWaitingStatus.REJECT)
                        .orElse(null)?: throw NoticeNotFoundException(noticeId.toString()))
                        .recruitmentBusiness!!
                )
            )
        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun removeTechnologySet(user: User, technologyName: String, noticeId: Long) {
        if (user is Company) {
            technologyUsageRepository.delete(
                technologyUsageRepository.findByIdOrNull(
                    TechnologyUsageIdClass(
                        technologyName,
                        user.id!!
                    )
                )?: return
            )

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
            return noticeRepository.findAllByIsApprove(NoticeWaitingStatus.WAITING, PageRequest.of(idx, size, Sort.by("createdAt").descending())).map {
                it.toMinimumNoticeResponse()
            }
        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun getMyNoticeList(user: User): List<NoticeWithIsApproveResponse> {
        if (user is Company) {
            return noticeRepository.findAllByCompanyOrderByCreatedAtDesc(user).map {
                it.toNoticeWithIsApproveResponse()
            }
        } else throw NoAuthenticationException(user.roleList.toString())
    }


    private fun checkAuthentication(current: User, notice: Notice): Boolean {
        return (current is Company && current.noticeList.contains(notice))
    }

    override fun getMinimumNoticeList(idx: Int, size: Int): Page<MinimumNoticeResponse> {
        return noticeRepository.findAllByIsApprove(NoticeWaitingStatus.APPROVE, PageRequest.of(idx, size, Sort.by("createdAt").descending())).map {
            it.toMinimumNoticeResponse()
        }
    }

    override fun getMaximumNotice(id: Long): MaximumNoticeWithoutPayResponse {
        return (noticeRepository.findByIdOrNull(id)?: throw NoticeNotFoundException(id.toString())).toMaximumNoticeWithoutPayResponse()
    }

    override fun  searchMinimumNoticeList(query: String): Page<MinimumNoticeResponse>? {
        try {
            return noticeSearchDocumentRepository.findAllBy(
                TextCriteria.forDefaultLanguage().matchingAny(query),
                PageRequest.of(0, 20)
            ).map { noticeSearchDocument ->
                noticeRepository.findById(noticeSearchDocument.noticeId).orElse(null)?.let {
                    (it.toMinimumNoticeResponse())
                }
            }
        } catch (e: java.lang.NullPointerException) {
            return null
        }
    }

    override fun printNotice(user: User, noticeId: Long): FileResponse {
        TODO("Not yet implemented")
    }

}