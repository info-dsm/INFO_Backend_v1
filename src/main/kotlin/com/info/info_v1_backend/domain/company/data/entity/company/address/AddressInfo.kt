package com.info.info_v1_backend.domain.company.data.entity.company.address

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class AddressInfo(
    fullAddress: String,
    addressNumber: Long
) {
    @Column(name = "full_address", insertable = false, updatable = false)
    var fullAddress: String = fullAddress
        protected set

    @Column(name = "address_number", insertable = false, updatable = false)
    var addressNumber: Long = addressNumber
        protected set
}