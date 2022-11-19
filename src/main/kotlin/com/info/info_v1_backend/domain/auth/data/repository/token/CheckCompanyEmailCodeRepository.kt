package com.info.info_v1_backend.domain.auth.data.repository.token

import com.info.info_v1_backend.domain.auth.data.entity.token.CheckCompanyEmailCode
import org.springframework.data.repository.CrudRepository

interface CheckCompanyEmailCodeRepository: CrudRepository<CheckCompanyEmailCode, String> {
}