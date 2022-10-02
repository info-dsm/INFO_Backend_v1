package com.info.info_v1_backend.domain.company.data.entity.notice.certificate

import com.info.info_v1_backend.domain.company.data.entity.notice.recruitment.RecruitmentBusiness
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


@Entity
class Certificate(
    name: String,
    recruitmentBusiness: RecruitmentBusiness
): BaseTimeEntity() {

    @Id
    @Column(name = "certificate_name", nullable = false)
    val name: String = name

    @ManyToOne
    @JoinColumn(name = "recruitment_business", nullable = false)
    val recruitmentBusiness: RecruitmentBusiness = recruitmentBusiness

}