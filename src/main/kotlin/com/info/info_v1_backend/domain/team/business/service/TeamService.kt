package com.info.info_v1_backend.domain.team.business.service

import com.info.info_v1_backend.domain.team.business.dto.request.RegisterTeamRequest

interface TeamService {

    fun registerTeam(request: RegisterTeamRequest)
}