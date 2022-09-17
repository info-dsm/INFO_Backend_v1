package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.Contactor
import com.info.info_v1_backend.domain.company.business.dto.request.notice.CloseNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.EditNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.RegisterNoticeRequest
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.notice.Pay
import com.info.info_v1_backend.domain.company.data.entity.notice.embeddable.TargetMajor
import com.info.info_v1_backend.domain.company.data.repository.notice.NoticeRepository
import com.info.info_v1_backend.domain.company.data.repository.notice.PayRepository
import com.info.info_v1_backend.domain.company.data.repository.notice.TargetMajorRepository
import com.info.info_v1_backend.domain.company.exception.CompanyNotFoundException
import com.info.info_v1_backend.domain.company.exception.IsNotContactorCompany
import com.info.info_v1_backend.global.util.user.CurrentUtil
import org.springframework.stereotype.Service

@Service
class NoticeServiceImpl(
    private val currentUtil: CurrentUtil,
    private val noticeRepository: NoticeRepository,
    private val payRepository: PayRepository,
    private val targetMajorRepository: TargetMajorRepository
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
                            r.workRemark,
                            r.commuteTime,
                            r.workTime,
                            r.screeningProcedure,
                            r.alternativeMilitaryPlan,
                            r.mealSupport,
                            r.welfare,
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

    override fun editNotice(request: EditNoticeRequest) {
        TODO("Not yet implemented")
    }

    override fun deleteNotice(noticeId: Long) {
        TODO("Not yet implemented")
    }

    override fun closeNotice(request: CloseNoticeRequest) {
        TODO("Not yet implemented")
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