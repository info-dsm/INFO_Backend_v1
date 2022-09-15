package com.info.info_v1_backend.domain.auth.data.repository.token

import com.info.info_v1_backend.domain.auth.data.entity.token.CheckEmailCode
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CheckEmailCodeRepository: CrudRepository<CheckEmailCode, String> {
}