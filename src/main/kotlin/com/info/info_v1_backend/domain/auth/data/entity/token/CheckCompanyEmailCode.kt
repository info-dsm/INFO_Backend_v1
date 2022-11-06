package com.info.info_v1_backend.domain.auth.data.entity.token

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(timeToLive = 600)
class CheckCompanyEmailCode(
    email: String,
    code: String,
) {
    @Id
    val email = email

    val code = code
}