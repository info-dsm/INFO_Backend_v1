package com.info.info_v1_backend.domain.company.data.entity.notice

import com.info.info_v1_backend.domain.company.business.dto.request.notice.edit.EditNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.response.notice.BigClassificationResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MaximumNoticeWithoutPayResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MinimumNoticeResponse
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.notice.applicant.Applicant
import com.info.info_v1_backend.domain.company.data.entity.notice.embeddable.*
import com.info.info_v1_backend.domain.company.data.entity.notice.file.FormAttachment
import com.info.info_v1_backend.domain.company.data.entity.notice.file.Reporter
import com.info.info_v1_backend.domain.company.data.entity.notice.interview.InterviewProcess
import com.info.info_v1_backend.domain.company.data.entity.notice.interview.InterviewProcessUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.recruitment.RecruitmentBusiness
import com.info.info_v1_backend.global.base.entity.BaseAuthorEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*


@Entity
@Table(name = "notice")
@SQLDelete(sql = "UPDATE `notice` SET notice_is_delete = true WHERE id = ?")
@Where(clause = "notice_is_delete = false")
class Notice(
    company: Company,
//    recruitmentBusinessList: MutableList<RecruitmentBusiness>,
    workTime: WorkTime,
    pay: Pay,

    mealSupport: MealSupport,
    welfare: Welfare,

    noticeOpenPeriod: NoticeOpenPeriod,

    interviewProcessList: List<InterviewProcessUsage>,

    needDocuments: String?,

    otherFeatures: String?,
    workPlace: WorkPlace,
    isPersonalContact: Boolean,


    ): BaseAuthorEntity() {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY,
    )
    val id: Long? = null

    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "company_id", nullable = false)
    val company: Company = company


    @OneToOne
    @JoinColumn(name = "recruitment_business_id", nullable = false)
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
    var interviewProcessList: MutableList<InterviewProcessUsage> = interviewProcessList as MutableList<InterviewProcessUsage>
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

    @OneToMany(mappedBy = "notice")
    var formAttachmentList: MutableList<FormAttachment> = ArrayList()
        protected set


    @Column(name = "notice_is_delete", nullable = false)
    var isDelete: Boolean = false
        protected set

    @Column(name = "notice_is_approve", nullable = false)
    var isApprove: Boolean = false
        protected set

    @OneToMany(mappedBy = "notice")
    var applicantList: MutableList<Applicant> = ArrayList()
        protected set


    fun addAttachment(formAttachment: FormAttachment) {
        this.formAttachmentList.add(formAttachment)
    }

    fun addRecruitmentBusiness(recruitmentBusiness: RecruitmentBusiness) {
        this.recruitmentBusiness = recruitmentBusiness
    }

    fun changeInterviewProcess(key: Int, interviewProcess: InterviewProcess) {
        this.interviewProcessList.first { it.sequence == key }.changeInterviewProcess(interviewProcess)
    }

    fun approveNotice() {
        this.isApprove = true
    }

    fun toMinimumNoticeResponse(): MinimumNoticeResponse {
        return MinimumNoticeResponse(
            this.id!!,
            this.company.toMinimumCompanyResponse(),
            this.recruitmentBusiness!!.toRecruitmentBusinessResponse()
        )
    }

    fun toMaximumNoticeResponse(): MaximumNoticeWithoutPayResponse {
        return MaximumNoticeWithoutPayResponse(
            this.id!!,
            this.company.toMaximumCompanyResponse(),
            this.recruitmentBusiness!!.toRecruitmentBusinessResponse(),
            this.workTime.toWorkTimeRequest(),
            this.mealSupport.toMealSupportRequest(),
            this.welfare.toWelfare(),
            this.noticeOpenPeriod.toNoticeOpenPeriod(),
            this.interviewProcessList.map {
                it.interviewProcess
            },
            this.needDocuments,
            this.otherFeatures,
            this.workPlace.toWorkPlaceRequest(),
            this.formAttachmentList.map {
                it.toFileResponse()
            }
        )
    }

    fun editNotice(r: EditNoticeRequest) {
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

}