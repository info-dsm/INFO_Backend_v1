package com.info.info_v1_backend.domain.auth.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.data.repository.token.CheckEmailCodeRepository
import com.info.info_v1_backend.domain.auth.data.repository.token.RefreshTokenRepository
import com.info.info_v1_backend.domain.auth.data.repository.user.UserRepository
import com.info.info_v1_backend.domain.auth.exception.CheckEmailCodeException
import com.info.info_v1_backend.domain.auth.presentation.dto.request.StudentSignUpRequest
import com.info.info_v1_backend.global.security.jwt.TokenProvider
import com.info.info_v1_backend.global.security.jwt.data.TokenResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val checkEmailCodeRepository: CheckEmailCodeRepository,
    private val userRepository: UserRepository<User>,
    private val tokenProvider: TokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,

) : AuthService {
    override fun studentSignUp(req: StudentSignUpRequest): TokenResponse {
        if((checkEmailCodeRepository.findById(req.email).orElse(null)?:throw CheckEmailCodeException(req.email)).code == req.emailCheckCode){
            val encPw = passwordEncoder.encode(req.password)


            val user = Student(
                req.studentKey,
                req.name,
                req.email,
                encPw,
                req.githubLink,
                null
            )
            userRepository.save(user)
            return tokenProvider.encode(user.id.toString())
        } else throw CheckEmailCodeException(req.emailCheckCode)
    }
}