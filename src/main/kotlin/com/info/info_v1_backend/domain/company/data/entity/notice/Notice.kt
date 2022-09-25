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
    resumeForm: File,

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

    @OneToOne(fetch = FetchType.LAZY)
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
        name = "notice_screening_process",
        joinColumns = [JoinColumn(name = "notice_id")]
    )
    @SortNatural
    var interviewProcessList: LinkedHashMap<Int, InterviewProcess> = LinkedHashMap()
        protected set


    var needDocuments: String? = needDocuments
        protected set

    @OneToMany
    var resumeForm: File? = resumeForm
        protected set

    @Column(name = "notice_other_features", nullable = true)
    var otherFeatures: String? = otherFeatures
        protected set

    var workPlace: WorkPlace = workPlace

    @Column(name = "is_personal_contact", nullable = false)
    var isPersonalContact: Boolean = isPersonalContact

    @OneToMany
    var reportedFileList: MutableList<File> = ArrayList()
        protected set

    @Column(name = "notice_is_delete", nullable = false)
    var isDelete: Boolean = false
        protected set

    var isApprove: Boolean = false
        protected set


    fun changeInterviewProcess(key: Int, interviewProcess: InterviewProcess) {
        this.interviewProcessList[key] = interviewProcess
    }

    fun approveNotice() {
        this.isApprove = true
    }






}