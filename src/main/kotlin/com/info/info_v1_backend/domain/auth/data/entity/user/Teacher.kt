package com.info.info_v1_backend.domain.auth.data.entity.user

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType


@Entity
@DiscriminatorValue("teacher")
@Inheritance(strategy = InheritanceType.JOINED)
class Teacher(
    name: String,
    email: String,
    password: String
): User(
    name,
    email,
    password,
    Role.TEACHER
) {

}