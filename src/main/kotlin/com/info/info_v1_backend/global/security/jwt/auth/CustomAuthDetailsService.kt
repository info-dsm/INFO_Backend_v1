package com.info.info_v1_backend.global.security.jwt.auth

import com.info.info_v1_backend.domain.auth.data.repository.user.UserRepository
import com.info.info_v1_backend.global.security.jwt.exception.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomAuthDetailsService(
    private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findById(username).map { CustomAuthDetails(it) }.orElse(null)?: throw UsernameNotFoundException(username)
    }
}