package com.info.info_v1_backend.domain.company.data.entity.notice

import com.info.info_v1_backend.domain.company.business.dto.request.notice.edit.EditNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MaximumNoticeWithPayResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MaximumNoticeWithoutPayResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MinimumNoticeResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.NoticeWithApproveStatusResponse
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.notice.applicant.Applicant
import com.info.info_v1_backend.domain.company.data.entity.notice.certificate.Certificate
import com.info.info_v1_backend.domain.company.data.entity.notice.embeddable.*
import com.info.info_v1_backend.domain.company.data.entity.notice.file.FormAttachment
import com.info.info_v1_backend.domain.company.data.entity.notice.interview.InterviewProcess
import com.info.info_v1_backend.domain.company.data.entity.notice.interview.InterviewProcessUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.recruitment.RecruitmentBusiness
import com.info.info_v1_backend.domain.company.data.entity.notice.technology.Technology
import com.info.info_v1_backend.global.base.entity.BaseAuthorEntity
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import com.info.info_v1_backend.global.error.common.InvalidParameterException
import org.hibernate.annotations.*
import org.springframework.http.HttpStatus
import java.time.DateTimeException
import java.time.LocalDate
import java.util.*
import javax.persistence.*
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.ValidationException
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


@SQLDelete(sql = "UPDATE `notice` SET notice_is_delete = true WHERE id = ?")
@Where(clause = "notice_is_delete = false")
@Entity
@Table(name = "notice")
class Notice(
    id: Long,
    company: Company,
//    recruitmentBusinessList: MutableList<RecruitmentBusiness>,
    workTime: WorkTime,
    pay: Pay,

    mealSupport: MealSupport,
    welfare: Welfare,

    noticeOpenPeriod: NoticeOpenPeriod,

    needDocuments: String?,

    otherFeatures: String?,
    workPlace: WorkPlace,
    isPersonalContact: Boolean,


): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = id

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "company_id", nullable = false)
    val company: Company = company


    @OneToOne
    @JoinColumn(name = "recruitment_business_id", nullable = true)
    var recruitmentBusiness: RecruitmentBusiness? = null
        protected set

    @Embedded
    var workTime: WorkTime = workTime
        protected set

    @Embedded
    var pay: Pay = pay
        protected set

    @Embedded
    var mealSupport: MealSupport = mealSupport
        protected set

    @Embedded
    var welfare: Welfare = welfare
        protected set

    @Embedded
    var noticeOpenPeriod: NoticeOpenPeriod = noticeOpenPeriod
        protected set


    @ElementCollection
    var interviewProcessList: MutableList<InterviewProcessUsage> = kotlin.collections.ArrayList()
        protected set

    @Column(name = "need_documents", nullable = true)
    var needDocuments: String? = needDocuments
        protected set


    @Column(name = "notice_other_features", nullable = true)
    var otherFeatures: String? = otherFeatures
        protected set

    @Embedded
    var workPlace: WorkPlace = workPlace

    @Column(name = "is_personal_contact", nullable = false)
    var isPersonalContact: Boolean = isPersonalContact

    @OneToMany(cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var formAttachmentList: MutableList<FormAttachment> = ArrayList()
        protected set


    @Column(name = "notice_is_delete", nullable = false)
    var isDelete: Boolean = false
        protected set

    @Column(name = "notice_is_approve", nullable = false)
    var approveStatus: NoticeWaitingStatus = NoticeWaitingStatus.WAITING
        protected set

    @OneToMany(mappedBy = "notice", cascade = [CascadeType.PERSIST])
    var applicantList: MutableList<Applicant> = ArrayList()
        protected set

    init {
        this.company.updateLastNoticeYear()
        if (LocalDate.now().isAfter(noticeOpenPeriod.startDate)) throw InvalidParameterException("notice open period: start date must after then now.")
    }


    fun addAttachment(formAttachment: FormAttachment) {
        this.formAttachmentList.add(formAttachment)
    }

    fun addRecruitmentBusiness(recruitmentBusiness: RecruitmentBusiness) {
        this.recruitmentBusiness = recruitmentBusiness
    }

    fun addInterviewProcessAll(interviewProcessList: List<InterviewProcess>) {
        interviewProcessList.map {
            this.interviewProcessList.add(
                InterviewProcessUsage(
                    interviewProcessList.indexOf(it) + 1,
                    it
                )
            )
        }

    }

    fun changeInterviewProcess(key: Int, interviewProcess: InterviewProcess) {
        this.interviewProcessList.firstOrNull { it.sequence == key }
            ?.changeInterviewProcess(interviewProcess)
            ?:this.interviewProcessList.add(
                InterviewProcessUsage(
                    this.interviewProcessList.size + 1,
                    interviewProcess
                )
            )
    }

    fun removeInterviewProcess(key: Int) {
        this.interviewProcessList.first {
            it.sequence == key
        }.let {
            this.interviewProcessList.remove(it)
        }
        this.interviewProcessList.filter {
            it.sequence >= key
        }.map {
            it.pullSequence(key)
        }
    }

    fun approveNotice() {
        this.approveStatus = NoticeWaitingStatus.APPROVE
    }

    fun rejectNotice() {
        this.approveStatus = NoticeWaitingStatus.REJECT
    }

    fun toMinimumNoticeResponse(): MinimumNoticeResponse {
        return MinimumNoticeResponse(
            this.id,
            this.company.toMinimumCompanyResponse(),
            this.recruitmentBusiness!!.toRecruitmentBusinessResponse(),
            this.applicantList.filter {
                !it.isDelete
            }.size
        )
    }

    fun toMaximumNoticeWithoutPayResponse(): MaximumNoticeWithoutPayResponse {
        return MaximumNoticeWithoutPayResponse(
            this.id!!,
            this.company.toMaximumCompanyResponse(),
            this.recruitmentBusiness!!.toRecruitmentBusinessResponse(),
            this.workTime.toWorkTimeRequest(),
            this.mealSupport.toMealSupportRequest(),
            this.welfare.toWelfare(),
            this.noticeOpenPeriod.toNoticeOpenPeriod(),
            this.interviewProcessList.map {
                return@map mapOf(Pair(it.sequence, it.interviewProcess.meaning))
            },
            this.needDocuments,
            this.otherFeatures,
            this.workPlace.toWorkPlaceRequest(),
            this.formAttachmentList.map {
                it.toFileResponse()
            },
            this.applicantList.filter {
                !it.isDelete
            }.size
        )
    }

    fun editNotice(r: EditNoticeRequest) {
        r.recruitmentBusiness?.let {
            this.recruitmentBusiness!!.editRecruitmentBusiness(it)
        }
        r.workTime?.let {
            this.workTime.editWorkTime(r.workTime)
        }
        r.pay?.let {
            this.pay
        }
        r.mealSupport?.let {
            this.mealSupport.editMealSupport(it)
        }
        r.welfare?.let {
            this.welfare.editWelfare(r.welfare)
        }
        r.needDocuments?.let {
            this.needDocuments = r.needDocuments
        }
        r.otherFeatures?.let {
            this.otherFeatures = r.otherFeatures
        }
        r.workPlace?.let {
            this.workPlace.editWorkPlace(r.workPlace)
        }
        r.isPersonalContact?.let {
            this.isPersonalContact = r.isPersonalContact
        }
    }

    fun toNoticeWithApproveStatusResponse(): NoticeWithApproveStatusResponse {
        return NoticeWithApproveStatusResponse(
            this.toMaximumNoticeWithPayResponse(),
            this.approveStatus
        )
    }

    fun toMaximumNoticeWithPayResponse(): MaximumNoticeWithPayResponse {
        return MaximumNoticeWithPayResponse(
            this.toMaximumNoticeWithoutPayResponse(),
            this.pay.toPayRequest()
        )
    }

}