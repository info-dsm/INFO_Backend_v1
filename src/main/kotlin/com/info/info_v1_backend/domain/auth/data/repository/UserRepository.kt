package com.info.info_v1_backend.domain.auth.data.repository

import com.info.info_v1_backend.domain.auth.data.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository<T: User>: JpaRepository<T, Long> {
}