package com.info.info_v1_backend.domain.company.data.entity.notice.language

import com.info.info_v1_backend.domain.company.business.dto.response.notice.LanguageResponse
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Language(
    name: String
) {

    @Id
    @Column(name = "language_name", nullable = false)
    val name: String = name


    @OneToMany
    var languageUsage: MutableList<LanguageUsage> = ArrayList()
        protected set

    fun toLanguageResponse(): LanguageResponse {
        return LanguageResponse(
            this.name
        )
    }

}