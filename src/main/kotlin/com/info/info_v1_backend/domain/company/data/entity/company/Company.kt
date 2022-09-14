package com.info.info_v1_backend.domain.company.data.entity.company

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.global.image.entity.File
import javax.persistence.*


@Entity
@DiscriminatorValue("company")
@Inheritance(strategy = InheritanceType.JOINED)
class Company(
    shortName: String,
    fullName: String,
    email: String,
    password: String,
    introduction: String,
    contactor: Contactor
): User(
    fullName,
    email,
    password,
    Role.COMPANY
){

    @Column(name = "company_short_name", nullable = false)
    val shortName: String = shortName

    @Column(name = "company_introduction", nullable = false)
    val introduction: String = introduction

    @OneToMany
    var photoList: MutableList<File> = ArrayList()

    @Embedded
    var contactor: Contactor = contactor




}