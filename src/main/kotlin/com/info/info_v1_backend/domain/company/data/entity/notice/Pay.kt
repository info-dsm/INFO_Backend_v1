package com.info.info_v1_backend.domain.company.data.entity.notice

import com.info.info_v1_backend.domain.company.business.dto.request.notice.edit.EditPayRequest
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.MapsId
import javax.persistence.OneToOne

@Embeddable
class Pay(
    fieldTrainingPayPerMonth: Long,
    yearPayStart: Long,
    yearPayEnd: Long,
    bonus: Long?
) {

    @Id
    val id: Long? = null

    @Column(name = "field_training_pay", nullable = false)
    var fieldTrainingPayPerMonth: Long = fieldTrainingPayPerMonth

    @Column(name = "year_pay_start", nullable = false)
    var yearPayStart: Long = yearPayStart

    @Column(name = "year_pay_end", nullable = false)
    var yearPayEnd: Long = yearPayEnd

    @Column(name = "bonus", nullable = true)
    var bonus: Long? = bonus


    fun editPay(r: EditPayRequest) {
        r.fieldTrainingPayPerMonth?.let {
            this.fieldTrainingPayPerMonth = r.fieldTrainingPayPerMonth
        }
        r.editFullTimeEmploymentPay?.let {
            it.bonus?.let {
                this.bonus = it
            }
            it.yearPayStart?.let {
                this.yearPayStart = it
            }
            it.yearPayEnd?.let {
                this.yearPayEnd =it
            }
        }
    }
}