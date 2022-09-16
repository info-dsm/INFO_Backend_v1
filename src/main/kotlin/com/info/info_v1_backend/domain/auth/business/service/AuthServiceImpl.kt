package com.info.info_v1_backend.domain.auth.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.Teacher
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.data.repository.token.CheckEmailCodeRepository
import com.info.info_v1_backend.domain.auth.data.repository.token.RefreshTokenRepository
import com.info.info_v1_backend.domain.auth.data.repository.user.UserRepository
import com.info.info_v1_backend.domain.auth.exception.CheckEmailCodeException
import com.info.info_v1_backend.domain.auth.exception.CheckTeacherCodeException
import com.info.info_v1_backend.domain.auth.exception.IncorrectPassword
import com.info.info_v1_backend.domain.auth.exception.UserNotFoundException
import com.info.info_v1_backend.domain.auth.presentation.dto.request.LoginRequest
import com.info.info_v1_backend.domain.auth.presentation.dto.request.StudentSignUpRequest
import com.info.info_v1_backend.domain.auth.presentation.dto.request.TeacherSingUpRequest
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
}