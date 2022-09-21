package com.info.info_v1_backend.domain.auth.presentation.dto.request

data class EditMyInfo(
        val name: String?,
        val isHired: Boolean?,
        val githubLink: String?,
)
