package com.info.info_v1_backend.domain.project.data.entity.project

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.team.data.Team
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType


@Entity
@DiscriminatorValue(value = "registered")
@Inheritance(strategy = InheritanceType.JOINED)
class RegisteredProject(
    name: String,
    shortContent: String,
    purpose: String,
    theoreticalBackground: String,
    process: String,
    result: String,
    conclusion: String,
    referenceList: MutableList<String>,
    developerList: MutableList<Student>,
    developTeam: Team,
    codeLinkList: MutableList<String>,
    tagList: MutableList<String>
): Project(
    name,
    shortContent,
    developerList,
    developTeam,
    codeLinkList,
    tagList
) {


}