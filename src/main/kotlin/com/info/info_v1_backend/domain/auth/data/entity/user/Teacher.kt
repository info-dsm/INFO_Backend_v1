package com.info.info_v1_backend.domain.auth.data.entity.user

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.util.UUID
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity


@Entity
@DiscriminatorValue("teacher")
@Where(clause = "user_is_delete = false")
@SQLDelete(sql = "UPDATE `user` SET user_is_delete = true where id = ?")
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