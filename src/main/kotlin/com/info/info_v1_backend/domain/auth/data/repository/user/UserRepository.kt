package com.info.info_v1_backend.domain.auth.data.repository.user

import com.info.info_v1_backend.domain.auth.data.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository<T: User>: JpaRepository<T, Long> {
    fun findByEmail(email: String): Optional<T>
}