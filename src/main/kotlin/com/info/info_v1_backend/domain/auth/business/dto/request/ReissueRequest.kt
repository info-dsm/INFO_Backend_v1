package com.info.info_v1_backend.domain.auth.business.dto.request

data class ReissueRequest(
    val accessToken: String,
    val refreshToken: String,
)
