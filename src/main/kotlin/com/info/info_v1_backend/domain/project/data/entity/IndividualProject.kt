package com.info.info_v1_backend.domain.project.data.entity

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("individual")
class IndividualProject(

): Project(
) {
}