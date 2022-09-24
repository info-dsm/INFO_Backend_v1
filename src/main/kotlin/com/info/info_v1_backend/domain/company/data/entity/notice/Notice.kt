package com.info.info_v1_backend.domain.company.data.entity.notice

import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.notice.certificate.Certificate
import com.info.info_v1_backend.domain.company.data.entity.notice.embeddable.*
import com.info.info_v1_backend.domain.company.data.entity.notice.language.LanguageUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.recruitment.RecruitmentBusiness
import com.info.info_v1_backend.domain.company.data.entity.notice.technology.Technology
import com.info.info_v1_backend.global.base.entity.BaseAuthorEntity
import com.info.info_v1_backend.global.file.entity.File
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*


@Entity
@SQLDelete(sql = "UPDATE shop SET notice_is_delete = true WHERE id = ?")
@Where(clause = "notice_is_delete = false")
class Notice(
    recruitmentBusinessList: MutableList<RecruitmentBusiness>,
    languageUsageList: Set<LanguageUsage>,
    technologyList: MutableList<Technology>,
    needCertificateList: MutableList<Certificate>,
    gradeCutLine: Int?,
    company: Company,
    workHour: Int,
    pay: Pay,

    mealSupport: MealSupport,
    welfare: Welfare,
    alternativeMilitaryPlan: Boolean,


    recruitmentPeriod: RecruitmentPeriod,

    interviewProcessList: LinkedHashMap<Int, interviewProcess>,

    resumeForm: File,

    otherFeatures: String?,
    workPlace: WorkPlace,
    isPersonalContact: Boolean,

//    screeningProcedure: ScreeningProcedure,


    //기재되지 않은 사항
    needDocuments: String?,

    ): BaseAuthorEntity() {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY,
    )
    val id: Long? = null

    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "company_id", nullable = false)
    val company: Company = company

    @OneToMany(mappedBy = "notice")
    var recruitmentBusinessList: MutableList<RecruitmentBusiness> = recruitmentBusinessList
        protected set


    @Column(name = "grade_cut_line", nullable = true)
    var gradeCutLine: Int? = gradeCutLine
        protected set

    @Column(name = "work_hour", nullable = false)
    var workHour: Int = workHour
        protected set

    @OneToOne
    var pay: Pay = pay
        protected set

    @Embedded
    var mealSupport: MealSupport = mealSupport
        protected set

    @Embedded
    var welfare: Welfare = welfare
        protected set

    @Column(name = "alternative_military_plan", nullable = false)
    var alternativeMilitaryPlan: Boolean = alternativeMilitaryPlan
        protected set

    @Embedded
    var recruitmentPeriod: RecruitmentPeriod = recruitmentPeriod
        protected set


    @OneToOne
    var resumeForm: File = resumeForm
        protected set

    @Column(name = "other_feature", nullable = true)
    var otherFeatures: String? = otherFeatures
        protected set

    @Embedded
    var workPlace: WorkPlace = workPlace
        protected set

    @Column(name = "is_personal_contact", nullable = false)
    var isPersonalContact: Boolean = isPersonalContact
        protected set

    @ElementCollection
    var needDocuments: MutableList<String> = ArrayList()
        protected set

    @OneToMany
    var reportedFileList: MutableList<File> = ArrayList()
        protected set

    @Column(name = "notice_is_delete", nullable = false)
    var isDelete: Boolean = false
        protected set

    var isApprove: Boolean = false
        protected set

    @ElementCollection
    @CollectionTable(
        name = "notice_screening_process",
        joinColumns = [JoinColumn(name = "notice_id")]
    )
    @OrderBy(value = "sequence_order ASC")
    @MapKeyColumn(name = "sequence_order")
    var interviewProcessList: LinkedHashMap<Int, interviewProcess> = LinkedHashMap()
        protected set


    fun changeInterviewProcess(key: Int, interviewProcess: interviewProcess) {
        this.interviewProcessList[key] = interviewProcess
    }

    fun approveNotice() {
        this.isApprove = true
    }


    init {
        needDocuments?. let {
            this.needDocuments.add(it)
        }
    }





}