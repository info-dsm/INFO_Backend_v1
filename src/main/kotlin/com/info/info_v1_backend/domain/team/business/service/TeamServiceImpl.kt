package com.info.info_v1_backend.domain.team.business.service

import com.info.info_v1_backend.domain.team.business.dto.request.RegisterTeamRequest
import com.info.info_v1_backend.domain.team.data.repository.TeamRepository
import org.springframework.stereotype.Service


@Service
class TeamServiceImpl(
    private val teamRepository: TeamRepository,

): TeamService {

    override fun registerTeam(request: RegisterTeamRequest) {
        
        teamRepository

    }
}