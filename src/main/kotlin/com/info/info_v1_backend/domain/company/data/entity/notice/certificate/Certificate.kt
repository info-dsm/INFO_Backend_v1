package com.info.info_v1_backend.domain.company.data.entity.notice.certificate

import com.info.info_v1_backend.domain.company.business.dto.response.notice.CertificateResponse
import com.info.info_v1_backend.domain.company.data.entity.notice.recruitment.RecruitmentBusiness
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany


@Entity
class Certificate(
    name: String
): BaseTimeEntity() {

    @Id
    @Column(name = "certificate_name", nullable = false)
    val name: String = name

    @OneToMany
    val certificateUsageList: MutableList<CertificateUsage> = ArrayList()

    fun toCertificateResponse(): CertificateResponse {
        return CertificateResponse(
            this.name
        )
    }
}