package com.info.info_v1_backend.domain.auth.data.entity.user

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import com.info.info_v1_backend.domain.team.data.Affiliation
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import javax.persistence.*


@Entity
@DiscriminatorColumn(name = "user_type")
abstract class User(
    name: String,
    email: String,
    password: String,
    role: Role
): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    var name: String = name
        protected set

    var email: String = email
        protected set

    var password: String = password
        protected set

    var role: Role = role
        protected set

    @OneToMany(cascade = [CascadeType.REMOVE])
    var affiliation: MutableList<Affiliation> = ArrayList()




}
