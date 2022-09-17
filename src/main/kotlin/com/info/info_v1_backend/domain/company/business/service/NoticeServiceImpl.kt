package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.Contactor
import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.data.repository.user.StudentRepository
import com.info.info_v1_backend.domain.auth.exception.UserNotFoundException
import com.info.info_v1_backend.domain.company.business.dto.request.notice.CloseNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.EditNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.RegisterNoticeRequest
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.notice.Pay
import com.info.info_v1_backend.domain.company.data.entity.notice.embeddable.*
import com.info.info_v1_backend.domain.company.data.repository.notice.NoticeRepository
import com.info.info_v1_backend.domain.company.data.repository.notice.PayRepository
import com.info.info_v1_backend.domain.company.data.repository.notice.TargetMajorRepository
import com.info.info_v1_backend.domain.company.exception.CompanyNotFoundException
import com.info.info_v1_backend.domain.company.exception.IsNotContactorCompany
import com.info.info_v1_backend.domain.company.exception.NoticeNotFoundException
import com.info.info_v1_backend.global.util.user.CurrentUtil
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class NoticeServiceImpl(
    private val currentUtil: CurrentUtil,
    private val noticeRepository: NoticeRepository,
    private val payRepository: PayRepository,
    private val targetMajorRepository: TargetMajorRepository,
    private val studentRepository: StudentRepository
): NoticeService {

    override fun registerNotice(r: RegisterNoticeRequest) {
        val current = currentUtil.getCurrentUser()
        if (current is Contactor) {
            (current.company?: throw CompanyNotFoundException(current.email)).let { company ->
                {

                    val notice = noticeRepository.save(
                        Notice(
                            company,
                            r.businessInformation,
                            r.certificateList,
                            r.cutLine,
                            r.personalRemark,
                            CommuteTime(
                                r.commuteTime.startTime,
                                r.commuteTime.endTime
                            ),
                            r.workTime,
                            ScreeningProcedure(
                                r.screeningProcedure.document,
                                r.screeningProcedure.technicalInterview,
                                r.screeningProcedure.physicalCheck,
                                r.screeningProcedure.assignment,
                                r.screeningProcedure.executiveInterview,
                                r.screeningProcedure.elseProcedure
                            ),
                            r.alternativeMilitaryPlan,
                            MealSupport(
                                r.mealSupport.mealSupportPay,
                                r.mealSupport.breakfast,
                                r.mealSupport.lunch,
                                r.mealSupport.dinner
                            ),
                            Welfare(
                                r.welfare.dormitorySupport,
                                r.welfare.selfDevelopmentPay,
                                r.welfare.equipmentSupport,
                                r.welfare.elseSupport
                            ),
                            r.needDocuments,
                            r.deadLine,
                            r.isAlwaysOpen,
                            r.interviewHopeMonth,
                            r.workHopeMonth
                        )
                    )
                    company.registerNotice(
                        notice
                    )
                    payRepository.save(
                        Pay(
                            r.fieldTrainingPay,
                            r.employmentPay,
                            notice
                        )
                    )
                    r.targetMajorList.map {
                        targetMajorRequest -> {
                            targetMajorRepository.save(
                                TargetMajor(
                                    targetMajorRequest.majorType,
                                    targetMajorRequest.count,
                                    notice
                                )
                            )
                        }
                    }
                }
            }
        } else throw IsNotContactorCompany(current.roleList.toString())
    }

    override fun editNotice(request: EditNoticeRequest, noticeId: Long) {
        val current = currentUtil.getCurrentUser()
        val notice = noticeRepository.findById(noticeId).orElse(null)?: throw NoticeNotFoundException(noticeId.toString())
        if (checkAuthentication(current, notice))

        request.targetMajorList.map {
            target -> {
                targetMajorRepository.findFirstByNoticeAndMajorOrderByCreatedDateDesc(notice, target.majorType)
                    .orElse(null)?: let {
                    targetMajorRepository.save(
                        TargetMajor(
                            target.majorType,
                            target.count,
                            notice
                        )
                    )
                }.editTargetMajor(target)
            }
        }

        payRepository.findById(noticeId).orElse(null)?: let {
            payRepository.save(
                Pay(
                    request.pay.fieldTrainingPay,
                    EmploymentPay(
                        request.pay.employmentPay.yearPay,
                        request.pay.employmentPay.monthPay,
                        request.pay.employmentPay.bonus,
                    ),
                    notice
                )
            )
        }. let{
            pay -> pay.editPay(request.pay)
        }


        notice.editNotice(request)
    }

    override fun deleteNotice(noticeId: Long) {
        val current = currentUtil.getCurrentUser()
        val notice = noticeRepository.findById(noticeId).orElse(null)?: throw NoticeNotFoundException(noticeId.toString())
        if (checkAuthentication(current, notice))
        notice.makeDelete()
    }

    override fun closeNotice(request: CloseNoticeRequest, noticeId: Long) {
        val current = currentUtil.getCurrentUser()
        val notice = noticeRepository.findById(noticeId).orElse(null)?: throw NoticeNotFoundException(noticeId.toString())
        if (checkAuthentication(current, notice))
        notice.makeExpired()

        val studentList: MutableList<Student> = ArrayList()
        request.studentIdList.map {
            studentList.add(studentRepository.findById(it).orElse(null)?: throw UserNotFoundException(it.toString()))
        }
        notice.company.hireStudentAll(studentList.toList())

    }

    private fun checkAuthentication(current: User, notice: Notice): Boolean {
        return ((current is Contactor) && (current.company?.noticeList?.contains(notice) == true))
    }
    override fun getMinimumNotice(idx: Int, size: Int) {
        TODO("Not yet implemented")
    }

    override fun getMaximumNotice(id: Long) {
        TODO("Not yet implemented")
    }

    override fun searchNotice(query: String) {
        TODO("Not yet implemented")
    }
}