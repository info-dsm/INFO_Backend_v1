package com.info.info_v1_backend.domain.project.data.entity.project

import com.info.info_v1_backend.domain.project.data.entity.Creation
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import com.info.info_v1_backend.domain.team.data.Team
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("individual")
class IndividualProject(
    name: String,
    shortContent: String,
    creationList: MutableList<Creation>,
    codeLinkList: MutableList<String>,
    tagList: MutableList<String>
): Project(
    name,
    shortContent,
    creationList,
    codeLinkList,
    tagList,
    ProjectStatus.INDIVIDUAL
) {
}