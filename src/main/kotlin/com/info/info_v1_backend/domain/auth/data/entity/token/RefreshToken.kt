package com.info.info_v1_backend.domain.auth.data.entity.token

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive


@RedisHash
class RefreshToken(
    id: String,
    token: String
) {
    @Id
    val id = id

    var token = token
        protected set

    @TimeToLive
    var ttl: Long = 604800
        protected set

    fun reset(token: String): RefreshToken{
        this.token = token
        this.ttl = 604800
        return this
    }
}