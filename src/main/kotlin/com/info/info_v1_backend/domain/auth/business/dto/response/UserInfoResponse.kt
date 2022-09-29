package com.info.info_v1_backend.domain.auth.business.dto.response

sealed class UserInfoResponse{
    abstract val name: String
    abstract val email: String
}
