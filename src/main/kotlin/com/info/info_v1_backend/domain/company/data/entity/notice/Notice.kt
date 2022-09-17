package com.info.info_v1_backend.domain.company.data.entity.notice

import com.info.info_v1_backend.domain.company.business.dto.request.notice.EditNoticeRequest
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.notice.embeddable.*
import com.info.info_v1_backend.domain.company.data.entity.type.WorkTime
import java.time.LocalDate
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne


@Entity
class Notice(
    company: Company,
    businessInformation: String,
    certificateList: MutableList<String>,
    cutLine: Int?,
    personalRemark: String,
    commuteTime: CommuteTime,
    workTime: WorkTime,
    screeningProcedure: ScreeningProcedure,
    alternativeMilitaryPlan: Boolean,
    mealSupport: MealSupport,
    welfare: Welfare,
    needDocuments: String?,
    deadLine: LocalDate?,
    isAlwaysOpen: Boolean,
    interviewHopeMonth: LocalDate?,
    workHopeMonth: LocalDate?

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "id", nullable = false)
    val company: Company = company

    @OneToMany(mappedBy = "notice")
    var targetMajorList: MutableList<TargetMajor> = ArrayList()
        protected set
    @Column(name = "business_information", length = 255)
    var businessInformation: String = businessInformation
        protected set

    @ElementCollection
    var certificateList: MutableList<String> = certificateList
        protected set

    @Column(name = "cut_line", nullable = false)
    var cutLine: Int? = cutLine
        protected set

    @Column(name = "work_remark", nullable = false)
    var personalRemark: String = personalRemark
        protected set

    @Embedded
    var commuteTime: CommuteTime = commuteTime
        protected set

    @Column(name = "work_time", nullable = false)
    var workTime: WorkTime = workTime
        protected set

    @OneToOne
    var pay: Pay? = null
        protected set

    @Embedded
    var screeningProcedure: ScreeningProcedure = screeningProcedure
        protected set

    @Column(name = "alternative_military_plan", nullable = false)
    var alternativeMilitaryPlan: Boolean = alternativeMilitaryPlan
        protected set

    @Embedded
    var mealSupport: MealSupport = mealSupport
        protected set

    @Embedded
    var welfare: Welfare = welfare
        protected set

    @Column(name = "need_documents", nullable = true)
    var needDocuments: String? = needDocuments
        protected set

    @Column(name = "dead_line", nullable = true)
    var deadLine: LocalDate? = deadLine
        protected set

    @Column(name = "is_always_open", nullable = false)
    var isAlwaysOpen: Boolean = isAlwaysOpen
        protected set

    @Column(name = "interview_hope_month", nullable = true)
    var interviewHopeMonth: LocalDate? = interviewHopeMonth
        protected set

    @Column(name = "work_hope_month", nullable = true)
    var workHopeMonth: LocalDate? = workHopeMonth
        protected set

    @Column(name = "notice_is_delete", nullable = false)
    var isDelete: Boolean = false
        protected set

    @Column(name = "notice_is_expired", nullable = false)
    var isExpired: Boolean = false
        protected set

    fun makeDelete() {
        this.isDelete = true
    }

    fun makeExpired() {
        this.isExpired = true
    }

    fun editNotice(r: EditNoticeRequest) {
        r.businessInformation?.let {
            this.businessInformation = it
        }
        r.certificateList?.let {
            this.certificateList = it
        }
        r.cutLine?.let {
            this.cutLine = it
        }
        r.personalRemark ?.let {
            this.personalRemark = it
        }
        r.commuteTime ?.let {
            this.commuteTime = CommuteTime(
                it.startTime,
                it.endTime
            )
        }
        r.workTime ?.let {
            this.workTime = it
        }
        r.screeningProcedure ?.let {
            this.screeningProcedure = ScreeningProcedure(
                it.document,
                it.technicalInterview,
                it.physicalCheck,
                it.assignment,
                it.executiveInterview,
                it.elseProcedure
            )
        }
        r.alternativeMilitaryPlan ?.let {
            this.alternativeMilitaryPlan = it
        }
        r.mealSupport ?.let {
            this.mealSupport = MealSupport(
                it.mealSupportPay,
                it.breakfast,
                it.lunch,
                it.dinner
            )
        }
        r.welfare ?.let {
            this.welfare = Welfare(
                it.dormitorySupport,
                it.selfDevelopmentPay,
                it.equipmentSupport,
                it.elseSupport
            )
        }
        r.needDocuments ?.let {
            this.needDocuments = it
        }
        r.deadLine ?.let {
            this.deadLine = it
        }
        r.isAlwaysOpen ?.let {
            this.isAlwaysOpen = it
        }
        r.interviewHopeMonth ?.let {
            this.interviewHopeMonth = it
        }
        r.workHopeMonth ?.let {
            this.workHopeMonth = it
        }

    }

}