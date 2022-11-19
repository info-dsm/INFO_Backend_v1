package com.info.info_v1_backend.domain.company.data.entity.company.tag

import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import org.springframework.data.domain.Persistable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table


@Entity
@IdClass(BusinessAreaTaggedIdClass::class)
@Table(name = "business_area_tagged")
class BusinessAreaTagged(
    businessArea: BusinessArea,
    company: Company
): BaseTimeEntity(), Persistable<String>, java.io.Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "business_area_id", nullable = false)
    var businessArea: BusinessArea = businessArea

    @Id
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    var company: Company = company

    override fun getId(): String? {
        return this.id
    }

    override fun isNew(): Boolean {
        return this.createdAt == null
    }


}