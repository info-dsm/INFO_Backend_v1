package com.info.info_v1_backend.domain.auth.data.entity.user

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import javax.persistence.*


@Entity
@DiscriminatorValue("student")
@Inheritance(strategy = InheritanceType.JOINED)
class Student(
  studentId: String,
  name: String,
  email: String,
  password: String,
  githubLink: String
): User(
    name,
    email,
    password,
    Role.STUDENT
) {
    val studentId: String = studentId

    @Column(name = "github_link", nullable = false)
    var githubLink: String = githubLink



}