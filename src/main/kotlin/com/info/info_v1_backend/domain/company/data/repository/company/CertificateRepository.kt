package com.info.info_v1_backend.domain.company.data.repository.company

import com.info.info_v1_backend.domain.company.data.entity.notice.certificate.Certificate
import org.springframework.data.jpa.repository.JpaRepository

interface CertificateRepository: JpaRepository<Certificate, String> {
}