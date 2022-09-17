package com.info.info_v1_backend.domain.company.data.entity.notice.embeddable

import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity

@Entity
class Pay(

) {
    

    @Column(name = "field_training_pay", nullable = false)
    var fieldTrainingPay: Long = fieldTrainingPay

    @Embedded
    var employmentPay: EmploymentPay = employmentPay
}