package com.info.info_v1_backend.domain.company.data.entity.notice.embeddable

import com.info.info_v1_backend.domain.company.business.dto.request.notice.edit.EditWelfareRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.WelfareRequest
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

    fun toWelfare(): WelfareRequest {
        return WelfareRequest(
            this.dormitorySupport,
            this.selfDevelopmentPay,
            this.equipmentSupport,
            this.youthTomorrowChaeumDeduction,
            this.alternativeMilitaryPlan,
            this.elseSupport
        )
    }

    fun editWelfare(r: EditWelfareRequest) {
        r.dormitorySupport?.let {
            this.dormitorySupport = it
        }
        r.selfDevelopmentPay?.let {
            this.selfDevelopmentPay = it
        }
        r.equipmentSupport?.let {
            this.equipmentSupport = it
        }
        r.youthTomorrowChaeumDeduction?.let {
            this.youthTomorrowChaeumDeduction = it
        }
        r.alternativeMilitaryPlan?.let {
            this.alternativeMilitaryPlan = it
        }
        r.elseSupport?.let {
            this.elseSupport = it
        }
    }

}