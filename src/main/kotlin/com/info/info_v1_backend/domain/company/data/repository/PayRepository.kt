package com.info.info_v1_backend.domain.company.data.repository

import com.info.info_v1_backend.domain.company.data.entity.notice.Pay
import org.springframework.data.jpa.repository.JpaRepository

interface PayRepository: JpaRepository<Pay, Long> {
}