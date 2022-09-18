package com.info.info_v1_backend.domain.company.data.entity.notice.embeddable

import com.info.info_v1_backend.domain.company.business.dto.request.notice.MealSupportRequest
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class MealSupport(
    @Column(name = "meal_support_pay")
    var mealSupportPay: Long,
    @Column(name = "breakfast_meal_support")
    var breakfast: Boolean,
    @Column(name = "lunch_meal_support")
    var lunch: Boolean,
    @Column(name = "dinner_meal_support")
    var dinner: Boolean
) {

    fun toMealSupportRequest(): MealSupportRequest {
        return MealSupportRequest(
            this.mealSupportPay,
            this.breakfast,
            this.lunch,
            this.dinner
        )
    }
}