package com.info.info_v1_backend.domain.company.data.repository

import com.info.info_v1_backend.domain.company.data.entity.company.Company
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyRepository: JpaRepository<Company, Long> {

}