package com.info.info_v1_backend.domain.auth.presentation.dto.response

sealed class GetUserInfo{
    abstract val name: String
    abstract val email: String
}
