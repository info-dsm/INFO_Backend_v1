package com.info.info_v1_backend.domain.auth.business.service

import com.info.info_v1_backend.domain.auth.data.entity.token.RefreshToken
import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.Teacher
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.data.repository.token.CheckEmailCodeRepository
import com.info.info_v1_backend.domain.auth.data.repository.token.RefreshTokenRepository
import com.info.info_v1_backend.domain.auth.data.repository.user.UserRepository
import com.info.info_v1_backend.domain.auth.exception.*
import com.info.info_v1_backend.domain.auth.presentation.dto.request.EditPasswordRequest
import com.info.info_v1_backend.domain.auth.presentation.dto.request.LoginRequest
import com.info.info_v1_backend.domain.auth.presentation.dto.request.ReissueRequest
import com.info.info_v1_backend.domain.auth.presentation.dto.request.StudentSignUpRequest
import com.info.info_v1_backend.domain.auth.presentation.dto.request.TeacherSingUpRequest
import com.info.info_v1_backend.global.security.jwt.TokenProvider
import com.info.info_v1_backend.global.security.jwt.data.TokenResponse
import com.info.info_v1_backend.global.security.jwt.exception.ExpiredTokenException
import com.info.info_v1_backend.global.util.user.UserCheckUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val checkEmailCodeRepository: CheckEmailCodeRepository,
    private val userRepository: UserRepository<User>,
    private val tokenProvider: TokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val current: UserCheckUtil,

    ) : AuthService {
    override fun studentSignUp(req: StudentSignUpRequest): TokenResponse {
        if((checkEmailCodeRepository.findById(req.email).orElse(null)?:
            throw CheckEmailCodeException(req.email)).code == req.emailCheckCode){
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

    override fun teacherSignUp(req: TeacherSingUpRequest): TokenResponse {
        if((checkEmailCodeRepository.findById(req.email).orElse(null)?:
            throw CheckEmailCodeException(req.email)).code == req.emailCheckCode){
            if(req.teacherCheckCode == "1111"){
                val encPw = passwordEncoder.encode(req.password)

                val user = Teacher(
                    req.name,
                    req.email,
                    encPw
                )
                userRepository.save(user)
                return tokenProvider.encode(user.id.toString())
            } else throw CheckTeacherCodeException(req.teacherCheckCode)
        } else throw CheckEmailCodeException(req.emailCheckCode)
    }

    override fun login(req: LoginRequest): TokenResponse {
        val user = userRepository.findByEmail(req.email)
            .orElse(null)?:throw UserNotFoundException(req.email)
        if(passwordEncoder.matches(req.password,user.password)){
            return tokenProvider.encode(user.id.toString())
        } else throw IncorrectPassword(req.password)
    }

    override fun editPassword(req: EditPasswordRequest) {
        val user = current.getCurrentUser()
        if((checkEmailCodeRepository.findById(user.email).orElse(null)?:
            throw CheckEmailCodeException(user.email)).code == req.code){
            val encPw = passwordEncoder.encode(req.password)
            user.editPassword(encPw)
        } else CheckPasswordCodeException(req.code)
    }
    override fun reissue(req: ReissueRequest): TokenResponse {
        if(tokenProvider.isExpired(req.refreshToken)) throw ExpiredTokenException(req.refreshToken)

        val userId = tokenProvider.decodeBody(req.accessToken).subject
        val user = userRepository.findById(userId.toLong()).orElse(null) ?: throw UserNotFoundException(userId)
        val tokenResponse = tokenProvider.encode(user.id.toString())
        val token = RefreshToken(user.id.toString(), tokenResponse.refreshToken)

        refreshTokenRepository.findById(user.id.toString()).map {
            it.reset(token.token)
        }.orElse(null) ?: refreshTokenRepository.save(token)
        return tokenResponse
    }
}