package com.info.info_v1_backend.domain.company.business.dto.request.notice.register

import com.info.info_v1_backend.domain.company.data.entity.notice.embeddable.MealSupport
import org.springframework.validation.annotation.Validated
import javax.persistence.Column
import javax.validation.Valid
import javax.validation.constraints.Min

@Validated
data class MealSupportRequest(
    @field:Valid
    @field:Min(0, message = "mealSupportPay는 0 이상이어야 합니다.")
    var mealSupportPay: Long,
    var breakfast: Boolean,
    var lunch: Boolean,
    var dinner: Boolean
) {
    fun toMealSupport(): MealSupport {
        return MealSupport(
            this.mealSupportPay,
            this.breakfast,
            this.lunch,
            this.dinner
        )
    }

}
