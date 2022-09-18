package com.info.info_v1_backend.domain.auth.presentation.dto.response

data class GetTeacherInfo(
        override val name: String,
        override val email: String
):GetUserInfo
