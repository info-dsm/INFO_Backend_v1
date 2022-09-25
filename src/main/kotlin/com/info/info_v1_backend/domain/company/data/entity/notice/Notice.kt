package com.info.info_v1_backend.domain.company.data.entity.notice

import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.notice.certificate.Certificate
import com.info.info_v1_backend.domain.company.data.entity.notice.certificate.CertificateUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.embeddable.*
import com.info.info_v1_backend.domain.company.data.entity.notice.language.LanguageUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.recruitment.RecruitmentBusiness
import com.info.info_v1_backend.domain.company.data.entity.notice.technology.TechnologyUsage
import com.info.info_v1_backend.global.base.entity.BaseAuthorEntity
import com.info.info_v1_backend.global.file.entity.File
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SortNatural
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

    interviewProcessMap: LinkedHashMap<Int, InterviewProcess>,

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


    @OneToMany
    var recruitmentBusinessList: MutableList<RecruitmentBusiness> = ArrayList()
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
    @CollectionTable(
        name = "interview_process",
        joinColumns = [JoinColumn(name = "notice_id")]
    )
    @OrderBy(value = "interview_process_sequence asc")
    @MapKeyColumn(name = "interview_process_sequence")
    @Column(name = "interview_process_value")
    var interviewProcessList: LinkedHashMap<Int, InterviewProcess> = interviewProcessMap
        protected set


    var needDocuments: String? = needDocuments
        protected set

    @OneToMany(mappedBy = "notice")
    var reporterList: MutableList<Reporter> = ArrayList()
        protected set

    @Column(name = "notice_other_features", nullable = true)
    var otherFeatures: String? = otherFeatures
        protected set

    var workPlace: WorkPlace = workPlace

    @Column(name = "is_personal_contact", nullable = false)
    var isPersonalContact: Boolean = isPersonalContact


    @OneToMany(mappedBy = "notice")
    var attachmentList: MutableList<Attachment> = ArrayList()
        protected set


    @Column(name = "notice_is_delete", nullable = false)
    var isDelete: Boolean = false
        protected set

    var isApprove: Boolean = false
        protected set

    fun addAttachment(attachment: Attachment) {
        this.attachmentList.add(attachment)
    }


    fun changeInterviewProcess(key: Int, interviewProcess: InterviewProcess) {
        this.interviewProcessList[key] = interviewProcess
    }

    fun approveNotice() {
        this.isApprove = true
    }






}