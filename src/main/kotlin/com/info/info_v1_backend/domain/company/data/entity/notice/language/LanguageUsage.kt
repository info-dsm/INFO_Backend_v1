package com.info.info_v1_backend.domain.company.data.entity.notice.language

import com.info.info_v1_backend.domain.company.data.entity.notice.recruitment.RecruitmentBusiness
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import org.springframework.data.domain.Persistable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "language_usage")
@IdClass(LanguageUsageIdClass::class)
class LanguageUsage(
    language: Language,
    recruitmentBusiness: RecruitmentBusiness
): BaseTimeEntity(), Persistable<String>, java.io.Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    val language: Language = language

    @Id
    @ManyToOne
    @JoinColumn(name = "recruitment_business_id", nullable = false)
    val recruitmentBusiness: RecruitmentBusiness = recruitmentBusiness

    override fun getId(): String? {
        return this.id
    }

    override fun isNew(): Boolean {
        return this.createdAt == null
    }


}