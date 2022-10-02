package com.info.info_v1_backend.domain.company.data.entity.company.tag

import com.info.info_v1_backend.domain.company.data.entity.company.Company
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


@Entity
class BusinessAreaTagged(
    businessArea: BusinessArea,
    company: Company
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne
    @JoinColumn(name = "business_tagged")
    var businessArea: BusinessArea = businessArea

    @ManyToOne
    @JoinColumn(name = "company")
    var company: Company = company


}