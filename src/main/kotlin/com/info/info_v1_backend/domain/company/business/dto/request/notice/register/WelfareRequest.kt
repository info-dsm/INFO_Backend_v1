package com.info.info_v1_backend.domain.company.business.dto.request.notice.register

import com.info.info_v1_backend.domain.company.data.entity.notice.embeddable.Welfare
import javax.validation.constraints.Size

data class WelfareRequest(
    val dormitorySupport: Boolean,
    val selfDevelopmentPay: Boolean,
    val equipmentSupport: Boolean,
    val youthTomorrowChaeumDeduction: Boolean,
    val alternativeMilitaryPlan: Boolean,
    @field:Size(max = 255, message = "elseSupport의 최대길이는 255입니다.")
    var elseSupport: String?
) {
    fun toWelfare(): Welfare {
        return Welfare(
            this.dormitorySupport,
            this.selfDevelopmentPay,
            this.equipmentSupport,
            this.youthTomorrowChaeumDeduction,
            this.alternativeMilitaryPlan,
            this.elseSupport
        )
    }
}
