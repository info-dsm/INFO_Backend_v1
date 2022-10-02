package com.info.info_v1_backend.domain.auth.business.dto.response

data class TeacherInfoResponse(
        override val name: String,
        override val email: String
): UserInfoResponse()
