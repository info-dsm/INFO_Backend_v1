package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
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
import com.info.info_v1_backend.domain.company.data.entity.notice.certificate.CertificateUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.classification.RecruitmentBigClassification
import com.info.info_v1_backend.domain.company.data.entity.notice.classification.RecruitmentSmallClassification
import com.info.info_v1_backend.domain.company.data.entity.notice.interview.InterviewProcess
import com.info.info_v1_backend.domain.company.data.entity.notice.language.Language
import com.info.info_v1_backend.domain.company.data.entity.notice.language.LanguageUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.recruitment.RecruitmentBusiness
import com.info.info_v1_backend.domain.company.data.entity.notice.technology.Technology
import com.info.info_v1_backend.domain.company.data.entity.notice.technology.TechnologyUsage
import com.info.info_v1_backend.domain.company.data.repository.notice.*
import com.info.info_v1_backend.domain.company.exception.AlreadyJudgedNoticeException
import com.info.info_v1_backend.domain.company.exception.AlreadyExistsException
import com.info.info_v1_backend.domain.company.exception.NoticeNotFoundException
import com.info.info_v1_backend.global.error.common.NoAuthenticationException
import com.info.info_v1_backend.global.file.dto.FileResponse
import com.info.info_v1_backend.global.file.repository.FileRepository
import com.info.info_v1_backend.infra.amazon.s3.S3Util
import com.mongodb.MongoQueryException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.UncategorizedMongoDbException
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
    private val certificateUsageRepository: CertificateUsageRepository,
): NoticeService {


    override fun getClassificationList(): List<ClassificationResponse> {
        return smallClassificationRepository.findAll().map {
            it.toClassificationResponse()
        }
    }

    override fun registerNotice(user: User, request: RegisterNoticeRequest, attachmentList: List<MultipartFile>): NoticeIdResponse {
        if (user is Company) {

            val id = kotlin.random.Random.nextLong(100000000, 999999999)

            val notice = noticeRepository.save(
                Notice(
                    id,
                    user,
                    request.workTime.toWorkTime(),
                    request.pay.toPay(),
                    request.mealSupport.toMealSupport(),
                    request.welfare.toWelfare(),
                    request.noticeOpenPeriod.toNoticeOpenPeriod(),
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

            notice.addInterviewProcessAll(
                request.interviewProcessList
            )

            attachmentList.map {
                notice.addAttachment(
                    formAttachmentRepository.save(
                        FormAttachment(
                            s3Util.uploadFile(it, "notice/${notice.id}", "attachment"),
                            notice
                        )
                    )
                )
            }

            request.recruitmentRequest.needCertificateList.map {
                certificateUsageRepository.save(
                    CertificateUsage(
                        certificateRepository.save(
                            certificateRepository.findByIdOrNull(it)
                                ?: Certificate(
                                    it,
                                )
                        ),
                        notice.recruitmentBusiness!!
                    )
                )
            }

            request.recruitmentRequest.languageList.map {
                languageUsageRepository.save(
                    LanguageUsage(
                        languageRepository.save(
                            languageRepository.findByIdOrNull(it)
                                ?: Language(
                                    it
                                )
                        ),
                        notice.recruitmentBusiness!!
                    )
                )
            }

            request.recruitmentRequest.technologyList.map {
                technologyUsageRepository.save(
                    TechnologyUsage(
                        technologyRepository.save(
                            technologyRepository.findByIdOrNull(it)
                                ?: Technology(
                                    it
                                )
                        ),
                        notice.recruitmentBusiness!!
                    )
                )
            }
            return NoticeIdResponse(id)
        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun changeAttachment(user: User, attachmentList: List<MultipartFile>, noticeId: Long) {
        if (user is Company) {
            val notice = user.noticeList.first {
                it.id == noticeId
            }
            notice.formAttachmentList.clear()
            attachmentList.map {
                notice.addAttachment(
                    formAttachmentRepository.save(
                        FormAttachment(
                            s3Util.uploadFile(it, "notice/${noticeId}", "attachment"),
                            notice
                        )
                    )
                )
            }

        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun changeInterviewProcess(user: User, interviewProcessMap: Map<Int, InterviewProcess>, noticeId: Long) {
        if (user is Company) {
            val notice = user.noticeList.firstOrNull {
                it.id!! == noticeId
            }?: throw NoticeNotFoundException(noticeId.toString())

            interviewProcessMap.map {
                notice.changeInterviewProcess(
                    it.key,
                    it.value
                )
            }

        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun removeInterviewProcess(user: User, interviewSequence: Int, noticeId: Long) {
        if (user is Company) {
            val notice = user.noticeList.first {
                it.id == noticeId
            }

            notice.removeInterviewProcess(interviewSequence)

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
            if (notice.recruitmentBusiness!!.needCertificateList.filter {
                it.certificate.name == certificateName
                }.isNotEmpty()) throw AlreadyExistsException(certificateName)


            notice.recruitmentBusiness!!.addNeedCertificate(
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
            )


        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun removeCertificate(user: User, certificateName: String, noticeId: Long) {
        if (user is Company) {
            val notice = noticeRepository.findByIdOrNull(noticeId)?: throw NoticeNotFoundException(noticeId.toString())
            certificateUsageRepository.deleteByCertificateAndRecruitmentBusiness(
                certificateRepository.findByIdOrNull(certificateName)?: return,
                notice.recruitmentBusiness!!
            )
        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun editNotice(user: User, request: EditNoticeRequest, noticeId: Long) {
        if (user is Company) {
            val notice = noticeRepository.findByIdAndCompanyAndApproveStatusNot(noticeId, user, NoticeWaitingStatus.REJECT).orElse(null)?: throw NoticeNotFoundException(noticeId.toString())
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
            val notice = noticeRepository.findByIdAndCompanyAndApproveStatusNot(noticeId, user, NoticeWaitingStatus.REJECT)
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
            val notice = noticeRepository.findByIdAndCompanyAndApproveStatusNot(noticeId, user, NoticeWaitingStatus.REJECT)
                .orElse(null)?: throw NoticeNotFoundException(noticeId.toString())

            if (notice.recruitmentBusiness!!.languageUsageList.filter {
                it.language.name == languageName
            }.isNotEmpty()) throw AlreadyExistsException(languageName)
            notice.recruitmentBusiness!!.addLanguage(
                languageUsageRepository.save(
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
            val notice = noticeRepository.findByIdAndCompanyAndApproveStatusNot(noticeId, user, NoticeWaitingStatus.REJECT)
                .orElse(null)?: throw NoticeNotFoundException(noticeId.toString())
            languageUsageRepository.deleteByLanguageAndRecruitmentBusiness(
                languageRepository.findByIdOrNull(languageName)?: return,
                notice.recruitmentBusiness!!
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
            val notice = (noticeRepository.findByIdAndCompanyAndApproveStatusNot(noticeId, user, NoticeWaitingStatus.REJECT)
                .orElse(null)?: throw NoticeNotFoundException(noticeId.toString()))
            if (notice.recruitmentBusiness!!.technologyList.filter {
                it.technology.name == technologyName
            }.isNotEmpty()) throw AlreadyExistsException(technologyName)
            notice.recruitmentBusiness!!.addTechnology(
                technologyUsageRepository.save(
                    TechnologyUsage(
                        technologyRepository.findByIdOrNull(
                            technologyName
                        ) ?: technologyRepository.save(
                            Technology(
                                technologyName
                            )
                        ),
                        notice.recruitmentBusiness!!
                    )
                )
            )
        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun removeTechnologySet(user: User, technologyName: String, noticeId: Long) {
        if (user is Company) {
            val notice = noticeRepository.findByIdAndCompanyAndApproveStatusNot(noticeId, user, NoticeWaitingStatus.REJECT)
                .orElse(null)?: throw NoticeNotFoundException(noticeId.toString())
            technologyUsageRepository.deleteByTechnologyAndRecruitmentBusiness(
                technologyRepository.findByIdOrNull(technologyName)?: return,
                notice.recruitmentBusiness!!
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
            if (notice.approveStatus == NoticeWaitingStatus.WAITING) {
                notice.approveNotice()
            } else throw AlreadyJudgedNoticeException(notice.approveStatus.name)
        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun rejectNotice(user: User, noticeId: Long) {
        val notice = noticeRepository.findByIdOrNull(noticeId) ?: throw NoticeNotFoundException(noticeId.toString())
        if (user is Teacher) {
            if (notice.approveStatus == NoticeWaitingStatus.WAITING) {
                notice.rejectNotice()
            } else throw AlreadyJudgedNoticeException(notice.approveStatus.name)
        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun getWaitingNoticeList(user: User, idx: Int, size: Int): Page<MinimumNoticeResponse> {
        if (user is Teacher) {
            return noticeRepository.findAllByApproveStatus(NoticeWaitingStatus.WAITING, PageRequest.of(idx, size, Sort.by("createdAt").descending())).map {
                it.toMinimumNoticeResponse()
            }
        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun getMyNoticeList(user: User): List<NoticeWithApproveStatusResponse> {
        if (user is Company) {
            return noticeRepository.findAllByCompanyOrderByCreatedAtDesc(user).map {
                it.toNoticeWithApproveStatusResponse()
            }
        } else throw NoAuthenticationException(user.roleList.toString())
    }


    private fun checkAuthentication(current: User, notice: Notice): Boolean {
        return (current is Company && current.noticeList.contains(notice))
    }

    override fun getMinimumNoticeList(idx: Int, size: Int): Page<MinimumNoticeResponse> {
        return noticeRepository.findAllByApproveStatus(NoticeWaitingStatus.APPROVE, PageRequest.of(idx, size, Sort.by("createdAt").descending())).map {
            it.toMinimumNoticeResponse()
        }
    }

    override fun getMaximumNotice(id: Long, user: User): MaximumNoticeWithoutPayResponse {

        val notice = (noticeRepository.findByIdOrNull(id)?: throw NoticeNotFoundException(id.toString()))

        if(notice.approveStatus == NoticeWaitingStatus.WAITING && user is Student) throw NoAuthenticationException(user.roleList.toString())
        return notice.toMaximumNoticeWithoutPayResponse()
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
        } catch (e: UncategorizedMongoDbException) {
            return null
        } catch(e: MongoQueryException){
            return null
        } catch (e: java.lang.NullPointerException) {
            return null
        }
    }

    override fun printNotice(user: User, noticeId: Long): FileResponse {
        TODO("Not yet implemented")
    }

}