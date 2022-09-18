package com.info.info_v1_backend.domain.team.presentation

import com.info.info_v1_backend.domain.team.business.dto.request.RegisterTeamRequest
import com.info.info_v1_backend.domain.team.business.service.TeamService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RequestMapping("/api/info/v1/team")
@RestController
@Validated
class TeamController(
    private val teamService: TeamService
) {


    @PostMapping
    fun registerTeam(
        @RequestBody request: RegisterTeamRequest
    ) {
        teamService.registerTeam(request)

    }


}