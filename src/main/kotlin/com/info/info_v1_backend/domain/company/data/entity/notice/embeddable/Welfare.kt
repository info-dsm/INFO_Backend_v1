package com.info.info_v1_backend.domain.company.data.entity.notice.embeddable

import com.info.info_v1_backend.domain.company.business.dto.request.notice.WelfareRequest
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
    @Column(name = "else_support", nullable = true, length = 255)
    var elseSupport: String?
) {

    fun toWelfareRequest(): WelfareRequest {
        return WelfareRequest(
            this.dormitorySupport,
            this.selfDevelopmentPay,
            this.equipmentSupport,
            this.elseSupport
        )
    }
}