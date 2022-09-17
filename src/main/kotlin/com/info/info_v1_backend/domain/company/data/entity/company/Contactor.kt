package com.info.info_v1_backend.domain.company.data.entity.company

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Contactor(
    @Column(name = "contactor_name", nullable = false)
    var name: String,
    @Column(name = "contactor_position", nullable = false)
    var position: String,
    @Column(name = "contactor_phone", nullable = false)
    var phone: String,
    @Column(name = "contactor_email", nullable = false)
    var email: String,
    @Column(name = "contactor_fax_address", nullable = true)
    var faxAddress: String
) {

}