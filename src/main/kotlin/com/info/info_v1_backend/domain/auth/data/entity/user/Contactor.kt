package com.info.info_v1_backend.domain.auth.data.entity.user

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
@DiscriminatorValue("contactor")
class Contactor(
    name: String,
    position: String,
    personalPhone: String,
    email: String,
    password: String
): User(
    name,
    email,
    password,
    Role.CONTACTOR
) {

    @Column(name = "contactor_position", nullable = false)
    var position: String = position
    @Column(name = "contactor_phone", nullable = false)
    var personalPhone: String = personalPhone

    @OneToOne
    var company: Company? = null


}