package com.info.info_v1_backend.domain.auth.data.entity.user

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.UUID
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity


@Entity
@DiscriminatorValue("teacher")
@OnDelete(action = OnDeleteAction.CASCADE)
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