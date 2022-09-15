package com.info.info_v1_backend.global.security.jwt.auth

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.data.repository.UserRepository
import com.info.info_v1_backend.domain.auth.exception.UserNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomAuthDetailsService(
    private val userRepository: UserRepository<User>
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findById(11).orElse(null)?: throw UserNotFoundException(username)
    }

}