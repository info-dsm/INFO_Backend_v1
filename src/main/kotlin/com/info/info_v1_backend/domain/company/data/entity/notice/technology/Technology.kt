package com.info.info_v1_backend.domain.company.data.entity.notice.technology

import com.info.info_v1_backend.domain.company.business.dto.response.notice.TechnologyResponse
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

    fun toTechnologyResponse(): TechnologyResponse {
        return TechnologyResponse(
            this.name
        )
    }


}