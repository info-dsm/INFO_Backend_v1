package com.info.info_v1_backend.domain.auth.data.repository.token

import com.info.info_v1_backend.domain.auth.data.entity.token.CheckPasswordCode
import org.springframework.data.repository.CrudRepository

interface CheckPasswordCodeRepository:CrudRepository<CheckPasswordCode, String> {
}