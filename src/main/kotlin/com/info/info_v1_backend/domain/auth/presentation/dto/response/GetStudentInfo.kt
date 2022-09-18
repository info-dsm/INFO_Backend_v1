package com.info.info_v1_backend.domain.auth.presentation.dto.response

import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse

data class GetStudentInfo(
        override val name: String,
        override val email: String,
        val studentKey: String,
        val githubLink: String,
        val isHired: Boolean?,
        val project: List<ProjectList>?,
        val company: MinimumCompanyResponse?
) :GetUserInfo