package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.Teacher
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.data.repository.user.StudentRepository
import com.info.info_v1_backend.domain.auth.exception.IsNotStudentException
import com.info.info_v1_backend.domain.auth.exception.UserNotFoundException
import com.info.info_v1_backend.domain.company.business.dto.request.notice.CloseNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.response.company.FieldTrainingResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.FieldTrainingStudentWithHiredResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.HiredStudentResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.ApplicantResponse
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.company.work.field.FieldTraining
import com.info.info_v1_backend.domain.company.data.entity.notice.applicant.Applicant
import com.info.info_v1_backend.domain.company.data.entity.notice.file.Reporter
import com.info.info_v1_backend.domain.company.data.repository.company.CompanyRepository
import com.info.info_v1_backend.domain.company.data.repository.company.FieldTrainingRepository
import com.info.info_v1_backend.domain.company.data.repository.company.HiredStudentRepository
import com.info.info_v1_backend.domain.company.data.repository.notice.ReporterFileRepository
import com.info.info_v1_backend.domain.company.data.repository.notice.ApplicantRepository
import com.info.info_v1_backend.domain.company.data.repository.notice.NoticeRepository
import com.info.info_v1_backend.domain.company.exception.*
import com.info.info_v1_backend.global.error.common.NoAuthenticationException
import com.info.info_v1_backend.infra.amazon.s3.S3Util
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.Year

@Service
@Transactional
class HireServiceImpl(
    private val fieldTrainingRepository: FieldTrainingRepository,
    private val companyRepository: CompanyRepository,
    private val studentRepository: StudentRepository,
    private val hiredStudentRepository: HiredStudentRepository,
    private val noticeRepository: NoticeRepository,
    private val applicantRepository: ApplicantRepository,
    private val reporterFileRepository: ReporterFileRepository,
    private val s3Util: S3Util
): HireService {

    override fun applyNotice(user: User, noticeId: Long, reporterList: List<MultipartFile>) {
        if (user is Student) {
            val notice = noticeRepository.findByIdOrNull(noticeId)?: throw NoticeNotFoundException(noticeId.toString())
            notice.applicantList.firstOrNull {
                it.student.id == user.id
            }?:let {
                val applicant = applicantRepository.save(
                    Applicant(
                        user,
                        notice,
                    )
                )

                reporterList.map {
                    reporterFileRepository.save(
                        Reporter(
                            s3Util.uploadFile(it, "notice/${notice.id}", "reporter"),
                            applicant
                        )
                    )
                }
            }

        } else throw IsNotStudentException(user.roleList.toString())
    }

    override fun getApplierList(user: User, noticeId: Long, idx: Int, size: Int): List<ApplicantResponse> {
        if (user is Company || user is Teacher) {
            val notice = noticeRepository.findByIdOrNull(noticeId)?: throw NoticeNotFoundException(noticeId.toString())

            return applicantRepository.findByNotice(
                notice
            ).map {
                it.toApplicantResponse()
            }
        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun cancelApply(user: User, noticeId: Long, studentId: Long) {
//        when (user) {
//            is Student -> {
//                val notice = noticeRepository.findByIdOrNull(noticeId)
//                    ?:throw NoticeNotFoundException(noticeId.toString())
//                applicantRepository.delete(
//                    user.applicantList.firstOrNull {
//                        (!it.isDelete) && (it.student.id == user.id) && (it.notice.id == notice.id)
//                    } ?: throw ApplicantUserNotFoundException("${user.id}, $noticeId")
//                )
//            }
//            is Teacher -> {
//                applicantRepository.delete(
//                    applicantRepository.findByNoticeAndStudent(
//
//                        noticeRepository.findByIdOrNull(noticeId)
//                            ?: throw NoticeNotFoundException(noticeId.toString()),
//
//                        studentRepository.findByIdOrNull(studentId)
//                            ?: throw UserNotFoundException(studentId.toString())
//
//                    ).orElseThrow { ApplicantUserNotFoundException("$studentId, $noticeId") }
//                )
//            }
//            else -> throw NoAuthenticationException(user.roleList.toString())
//        }
        val notice = noticeRepository.findByIdOrNull(noticeId)
            ?: throw NoticeNotFoundException(noticeId.toString())
        val user2 = if(user is Student && studentId == user.id) {
            user
        }else if (user is Teacher){
            studentRepository.findByIdOrNull(studentId)
                ?:throw UserNotFoundException(studentId.toString())
        }else{
            throw NoAuthenticationException(user.roleList.toString())
        }
        applicantRepository.delete(
            applicantRepository.findByNoticeAndStudent(
                notice,
                user2
            ).orElseThrow { ApplicantUserNotFoundException("$studentId , $noticeId") }
        )
    }

    override fun getFieldTrainingStudentList(user: User, companyId: Long): List<FieldTrainingResponse> {
        if (user is Student) throw NoAuthenticationException(user.roleList.toString())
        else {
            companyRepository.findByIdOrNull(companyId)?. let {
                return it.fieldTrainingList.filter { it1 ->
                    !it1.isDelete && !it1.isLinked && it1.createdAt!!.year == LocalDateTime.now().year
                }.map { it1 ->
                    it1.toFieldTrainingResponse()
                }
            }?: throw CompanyNotFoundException(companyId.toString())
        }
    }

    override fun makeFieldTrainingAndCloseNotice(user: User, request: CloseNoticeRequest, noticeId: Long) {
        if (user is Company || user is Teacher) {
            val notice = noticeRepository.findByIdOrNull(noticeId)
                ?: throw NoticeNotFoundException(noticeId.toString())

            if(user is Company && user != notice.company){
                throw NoAuthenticationException(user.name)
            }

            notice.applicantList.filter {
                request.studentIdList.contains(it.student.id!!)
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

            applicantRepository.deleteAll(notice.applicantList)

            noticeRepository.delete(notice)

        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun fireFieldTrainingStudent(user: User, studentId: Long, companyId: Long) {
        when (user) {
            is Company -> {
                (
                        (fieldTrainingRepository.findByStudentAndCompany(
                            studentRepository.findByIdOrNull(studentId)
                                ?: throw UserNotFoundException(studentId.toString()),
                            user
                        )).orElseThrow { FieldTrainingNotFoundException("$studentId, $companyId") }
                        ).makeNoLinked()
            }

            is Teacher -> {
                val company = companyRepository.findByIdOrNull(companyId)
                    ?: throw CompanyNotFoundException(companyId.toString())
                (
                        fieldTrainingRepository.findByStudentAndCompany(
                            studentRepository.findByIdOrNull(studentId)
                                ?: throw UserNotFoundException(studentId.toString()),
                            company
                        ).orElseThrow { FieldTrainingNotFoundException("$studentId, $companyId") }
                        ).makeNoLinked()

            }

            else -> throw NoAuthenticationException(user.roleList.toString())
        }
    }

    override fun getHiredStudentList(user: User, companyId: Long): List<HiredStudentResponse> {
        if (user is Student) throw NoAuthenticationException(user.roleList.toString())
        else {
            companyRepository.findByIdOrNull(companyId)?. let {
                return it.hiredStudentList.filter { it1 ->
                    !it1.isDelete && !it1.isFire && it1.createdAt!!.year == LocalDateTime.now().year
                }.map { it1 ->
                    it1.toHiredStudentResponse()
                }
            }?: throw CompanyNotFoundException(companyId.toString())
        }
    }

    override fun hireStudent(user: User, studentId: Long, companyId: Long, startDate: LocalDate) {
        when (user) {
            is Company -> {
                val training = user.fieldTrainingList.firstOrNull {
                    it.student.id!! == studentId
                } ?: throw IsNotFieldTrainingException(studentId.toString())

                training.let {
                    hiredStudentRepository.save(
                        it.toHiredStudent(startDate)
                    )
                }

            }

            is Teacher -> {
                val company = companyRepository.findByIdOrNull(companyId)
                    ?: throw CompanyNotFoundException(companyId.toString())

                val training = company.fieldTrainingList.firstOrNull {
                    it.student.id!! == studentId
                } ?: throw IsNotFieldTrainingException(studentId.toString())

                training.let {
                    hiredStudentRepository.save(
                        it.toHiredStudent(startDate)
                    )
                }

            }

            else -> throw NoAuthenticationException(user.roleList.toString())
        }
    }

    override fun fireStudent(user: User, studentId: Long, companyId: Long) {
        when (user) {
            is Company -> {
                (hiredStudentRepository.findByStudentAndCompany(
                    studentRepository.findByIdOrNull(studentId)?: throw UserNotFoundException(studentId.toString()),
                    user
                ).orElse(null)?: throw HiredStudentNotFoundException(studentId.toString())
                        ).makeFire()
            }

            is Teacher -> {
                (hiredStudentRepository.findByStudentAndCompany(

                    studentRepository.findByIdOrNull(studentId)
                        ?: throw UserNotFoundException(studentId.toString()),

                    companyRepository.findByIdOrNull(companyId)
                        ?: throw CompanyNotFoundException(companyId.toString())

                ).orElseThrow { HiredStudentNotFoundException(studentId.toString()) }
                        ).makeFire()
            }
            else -> throw NoAuthenticationException(user.roleList.toString())
        }
    }

    override fun getFieldTrainingStudentWithHiredListInThisYear(
        user: User,
        idx: Int,
        size: Int,
        year: Year
    ): Page<FieldTrainingStudentWithHiredResponse> {
        return fieldTrainingRepository.findAllByCreatedAtBetween(
            LocalDateTime.of(year.value, Month.JANUARY, 1, 0, 0, 0),
            LocalDateTime.of(year.value, Month.DECEMBER, 31, 23, 59, 59),
            PageRequest.of(idx, size)).map {
            it.toFieldTrainingStudentWithHiredResponse()
        }
    }

}