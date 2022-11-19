package com.info.info_v1_backend.domain.company.data.entity.company.embeddable

import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyContactRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.EditCompanyContactRequest
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class CompanyContact(
    contactorName: String,
    contactorRank: String?,
    phoneNumber: String,
    contactorPhone: String?,
    email: String
) {

    @Column(name = "contactor_name", nullable = false)
    var contactorName: String = contactorName
        protected set

    @Column(name = "contactor_rank", nullable = true)
    var contactorRank: String? = contactorRank
        protected set

    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String = phoneNumber
        protected set

    @Column(name = "contactor_phone", nullable = true)
    var contactorPhone: String? = contactorPhone
        protected set

    @Column(name = "email", nullable = false)
    var email: String = email
        protected set

    fun toCompanyContactRequest(): CompanyContactRequest {
        return CompanyContactRequest(
            this.contactorName,
            this.contactorRank,
            this.phoneNumber,
            this.contactorPhone,
            this.email,
        )
    }

    fun editCompanyContact(r: EditCompanyContactRequest) {
        r.contactorName?.let {
            this.contactorName = it
        }
        r.contactorRank?.let {
            this.contactorRank = it
        }
        r.phoneNumber?.let {
            this.phoneNumber = it
        }
        r.contactorPhone?.let {
            this.contactorPhone = it
        }
        r.email?.let {
            this.email = it
        }
    }
}