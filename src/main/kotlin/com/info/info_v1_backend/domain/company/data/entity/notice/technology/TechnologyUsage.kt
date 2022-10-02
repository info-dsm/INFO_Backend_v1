package com.info.info_v1_backend.domain.company.data.entity.notice.technology

import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.notice.recruitment.RecruitmentBusiness
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import org.springframework.data.domain.Persistable
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table


@Entity
@IdClass(TechnologyUsageIdClass::class)
@Table(name = "technology_usage")
class TechnologyUsage(
    technology: Technology,
    recruitmentBusiness: RecruitmentBusiness
): BaseTimeEntity(), Persistable<String>, Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "technology_id", nullable = false)
    val technology: Technology = technology

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