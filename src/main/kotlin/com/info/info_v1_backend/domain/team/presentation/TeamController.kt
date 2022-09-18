package com.info.info_v1_backend.domain.team.presentation

import com.info.info_v1_backend.domain.team.business.dto.request.EditTeamRequest
import com.info.info_v1_backend.domain.team.business.dto.request.RegisterTeamRequest
import com.info.info_v1_backend.domain.team.business.service.TeamService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RequestMapping("/api/info/v1/team")
@RestController
@Validated
class TeamController(
    private val teamService: TeamService
) {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registerTeam(
        @RequestBody request: RegisterTeamRequest
    ) {
        teamService.registerTeam(request)
    }

    @PatchMapping
    fun editTeam(
        @RequestBody request: EditTeamRequest,
        @RequestParam teamId: Long
    ) {
        teamService.editTeam(request, teamId)
    }


}