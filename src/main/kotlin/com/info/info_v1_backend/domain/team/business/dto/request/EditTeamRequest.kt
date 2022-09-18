package com.info.info_v1_backend.domain.team.business.dto.request

import java.time.YearMonth

data class EditTeamRequest(
    val name: String?,
    val establishedAt: YearMonth?,
    val githubLink: String?
)
