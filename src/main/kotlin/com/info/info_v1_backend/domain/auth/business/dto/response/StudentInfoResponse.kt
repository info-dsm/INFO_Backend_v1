package com.info.info_v1_backend.domain.auth.business.dto.response

import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse

data class StudentInfoResponse(
    override val name: String,
    override val email: String,
    val studentKey: String,
    val isFieldTraining: Boolean,
    val isHired: Boolean,
    val company: MinimumCompanyResponse?
) : UserInfoResponse()