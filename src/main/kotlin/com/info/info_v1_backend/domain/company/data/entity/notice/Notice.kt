package com.info.info_v1_backend.domain.company.data.entity.notice

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
    workRemark: String,
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
    var company: Company = company

    @OneToMany(mappedBy = "notice")
    var targetMajorList: MutableList<TargetMajor> = ArrayList()

    @Column(name = "business_information")
    var businessInformation: String = businessInformation

    @ElementCollection
    var certificateList: MutableList<String> = certificateList

    @Column(name = "cut_line", nullable = false)
    var cutLine: Int? = cutLine

    @Column(name = "work_remark", nullable = false)
    var workRemark: String = workRemark

    @Embedded
    var commuteTime: CommuteTime = commuteTime

    @Column(name = "work_time", nullable = false)
    var workTime: WorkTime = workTime

    @OneToOne
    var pay: Pay? = null

    @Embedded
    var screeningProcedure: ScreeningProcedure = screeningProcedure

    @Column(name = "alternative_military_plan", nullable = false)
    var alternativeMilitaryPlan: Boolean = alternativeMilitaryPlan

    @Embedded
    var mealSupport: MealSupport = mealSupport

    @Embedded
    var welfare: Welfare = welfare

    @Column(name = "need_documents", nullable = true)
    var needDocuments: String? = needDocuments

    @Column(name = "dead_line", nullable = true)
    var deadLine: LocalDate? = deadLine

    @Column(name = "is_always_open", nullable = false)
    var isAlwaysOpen: Boolean = isAlwaysOpen

    @Column(name = "interview_hope_month", nullable = true)
    var interviewHopeMonth: LocalDate? = interviewHopeMonth

    @Column(name = "work_hope_month", nullable = true)
    var workHopeMonth: LocalDate? = workHopeMonth


}