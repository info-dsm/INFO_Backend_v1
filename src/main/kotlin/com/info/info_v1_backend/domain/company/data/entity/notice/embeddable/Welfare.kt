package com.info.info_v1_backend.domain.company.data.entity.notice.embeddable

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Welfare(
    @Column(name = "dormitory_support", nullable = false)
    var dormitorySupport: Boolean,
    @Column(name = "self_development_pay", nullable = false)
    var selfDevelopmentPay: Boolean,
    @Column(name = "equipment_support", nullable = false)
    var equipmentSupport: Boolean,
    @Column(name = "youth_tomorrow_chaeum_deduction", nullable = false)
    var youthTomorrowChaeumDeduction: Boolean,
    @Column(name = "alternative_military_plan", nullable = false)
    var alternativeMilitaryPlan: Boolean,
    @Column(name = "else_support", nullable = true, length = 255)
    var elseSupport: String?,
) {

}