package com.info.info_v1_backend.domain.auth.presentation.dto.response

import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse

data class GetContactor(
        override val name: String,
        override val email: String,
        val position: String,
        val personalPhone: String,
        val company: MinimumCompanyResponse?
        ):GetUserInfo