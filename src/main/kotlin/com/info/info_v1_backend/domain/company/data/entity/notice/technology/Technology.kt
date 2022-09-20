package com.info.info_v1_backend.domain.company.data.entity.notice.technology

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Technology(
    name: String
) {
    @Id
    @Column(name = "technology_name", nullable = false)
    val name: String = name



}