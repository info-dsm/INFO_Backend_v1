package com.info.info_v1_backend.domain.company.data.entity.notice

import com.info.info_v1_backend.domain.company.business.dto.request.notice.PayRequest
import com.info.info_v1_backend.domain.company.data.entity.notice.embeddable.EmploymentPay
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.MapsId
import javax.persistence.OneToOne

@Entity
class Pay(
    fieldTrainingPayPerMonth: Long,
    fullTimeEmploymentPay: EmploymentPay,
    notice: Notice
) {

    @Id
    val id: Long? = null

    @Column(name = "field_training_pay", nullable = false)
    var fieldTrainingPayPerMonth: Long = fieldTrainingPayPerMonth

    @Embedded
    var fullTimeEmploymentPay: EmploymentPay = fullTimeEmploymentPay

    @OneToOne @MapsId
    var notice: Notice = notice

}