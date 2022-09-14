package com.info.info_v1_backend.domain.company.data.entity.notice

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
    @Column(name = "else_support", nullable = true)
    var elseSupport: String?
) {
}