package com.info.info_v1_backend.domain.team.data.repository

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.team.data.entity.Team
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface TeamRepository: JpaRepository<Team, Long> {

    fun findTeamByHeaderAndId(student: Student, id: Long): Optional<Team>
}