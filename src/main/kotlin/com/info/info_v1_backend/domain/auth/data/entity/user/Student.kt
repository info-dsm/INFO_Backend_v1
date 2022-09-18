package com.info.info_v1_backend.domain.auth.data.entity.user

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.project.data.entity.Creation
import com.info.info_v1_backend.domain.team.data.entity.Affiliation
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
    creationList: MutableList<Creation>?,
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
    var creationList: MutableList<Creation>? = creationList

    @OneToMany(cascade = [CascadeType.REMOVE])
    var affiliation: MutableList<Affiliation> = ArrayList()

    @ManyToOne
    var company: Company? = null

}