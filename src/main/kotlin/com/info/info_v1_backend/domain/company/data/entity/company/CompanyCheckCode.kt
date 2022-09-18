package com.info.info_v1_backend.domain.company.data.entity.company

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "company_check_code")
class CompanyCheckCode(
    code: String
) {

    @Id
    val code: String = code

    @Column(name = "is_used", nullable = false)
    var isUsed: Boolean = false
        protected set

    fun makeUsed() {
        this.isUsed = true
    }


}