package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.Contactor
import com.info.info_v1_backend.domain.company.business.dto.request.notice.RegisterNoticeRequest
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.notice.Pay
import com.info.info_v1_backend.domain.company.data.repository.NoticeRepository
import com.info.info_v1_backend.domain.company.data.repository.PayRepository
import com.info.info_v1_backend.domain.company.exception.CompanyNotFoundException
import com.info.info_v1_backend.domain.company.exception.IsNotContactorCompany
import com.info.info_v1_backend.global.util.user.CurrentUtil
import org.springframework.stereotype.Service

@Service
class NoticeServiceImpl(
    private val currentUtil: CurrentUtil,
    private val noticeRepository: NoticeRepository,
    private val payRepository: PayRepository
): NoticeService {

    override fun registerNotice(r: RegisterNoticeRequest) {
        val current = currentUtil.getCurrentUser()
        if (current is Contactor) {
            (current.company?: throw CompanyNotFoundException(current.email)).let { company ->
                {
                    val notice = noticeRepository.save(
                        Notice(
                            company,
                            r.targetMajorList,
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
                }
            }
        } else throw IsNotContactorCompany(current.roleList.toString())
    }
}