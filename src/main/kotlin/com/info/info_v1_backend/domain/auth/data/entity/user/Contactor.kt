package com.info.info_v1_backend.domain.auth.data.entity.user

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
@DiscriminatorValue("contactor")
@OnDelete(action = OnDeleteAction.CASCADE)
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

    @ManyToOne
    var company: Company? = null


}