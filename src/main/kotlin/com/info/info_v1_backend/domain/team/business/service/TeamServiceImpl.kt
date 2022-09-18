package com.info.info_v1_backend.domain.team.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.exception.IsNotStudentException
import com.info.info_v1_backend.domain.team.business.dto.request.RegisterTeamRequest
import com.info.info_v1_backend.domain.team.data.entity.Team
import com.info.info_v1_backend.domain.team.data.repository.TeamRepository
import com.info.info_v1_backend.global.util.user.CurrentUtil
import org.springframework.stereotype.Service


@Service
class TeamServiceImpl(
    private val teamRepository: TeamRepository,
    private val currentUtil: CurrentUtil

): TeamService {

    override fun registerTeam(request: RegisterTeamRequest) {
        val current = currentUtil.getCurrentUser()

        if (current is Student) {
            teamRepository.save(
                Team(
                    request.name,
                    request.establishedAt,
                    request.githubLink,
                    current
                )
            )
        }
        throw IsNotStudentException(current.roleList.toString())
    }






}