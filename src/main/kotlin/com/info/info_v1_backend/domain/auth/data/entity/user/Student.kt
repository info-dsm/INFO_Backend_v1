package com.info.info_v1_backend.domain.auth.data.entity.user

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import com.info.info_v1_backend.domain.project.data.entity.Creation
import com.info.info_v1_backend.domain.team.data.Affiliation
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import javax.persistence.*


@Entity
@DiscriminatorValue("student")
@Inheritance(strategy = InheritanceType.JOINED)
class Student(
  studentKey: String,
  name: String,
  email: String,
  password: String,
  githubLink: String,
  creationList: MutableList<Creation>
): User(
    name,
    email,
    password,
    Role.STUDENT
) {
    val studentKey: String = studentKey

    @Column(name = "github_link", nullable = false)
    var githubLink: String = githubLink

    @OneToMany(mappedBy = "student", cascade = [CascadeType.REMOVE])
    var creationList: MutableList<Creation> = creationList

    @OneToMany(cascade = [CascadeType.REMOVE])
    var affiliation: MutableList<Affiliation> = ArrayList()



}