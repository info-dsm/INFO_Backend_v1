package com.info.info_v1_backend.domain.team.data.repository

import com.info.info_v1_backend.domain.team.data.entity.Team
import org.springframework.data.jpa.repository.JpaRepository

interface TeamRepository: JpaRepository<Team, Long> {
}