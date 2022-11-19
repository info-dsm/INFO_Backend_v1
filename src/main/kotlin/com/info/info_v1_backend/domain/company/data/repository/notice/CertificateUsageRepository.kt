package com.info.info_v1_backend.domain.company.data.repository.notice

import com.info.info_v1_backend.domain.company.data.entity.notice.certificate.Certificate
import com.info.info_v1_backend.domain.company.data.entity.notice.certificate.CertificateUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.certificate.CertificateUsageIdClass
import com.info.info_v1_backend.domain.company.data.entity.notice.recruitment.RecruitmentBusiness
import org.springframework.data.jpa.repository.JpaRepository

interface CertificateUsageRepository: JpaRepository<CertificateUsage, CertificateUsageIdClass> {

    fun deleteByCertificateAndRecruitmentBusiness(certificate: Certificate, recruitmentBusiness: RecruitmentBusiness)
}