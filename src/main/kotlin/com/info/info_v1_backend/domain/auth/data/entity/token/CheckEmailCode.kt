package com.info.info_v1_backend.domain.auth.data.entity.token

import org.springframework.data.redis.core.RedisHash
import org.springframework.data.annotation.Id

@RedisHash(timeToLive = 300)
class CheckEmailCode(
    email:String,
    code: String,
) {
    @Id
    val email = email

    var code = code

}