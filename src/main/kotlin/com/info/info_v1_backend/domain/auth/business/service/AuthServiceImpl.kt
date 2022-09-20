package com.info.info_v1_backend.domain.auth.business.service

import com.info.info_v1_backend.domain.auth.data.entity.token.RefreshToken
import com.info.info_v1_backend.domain.auth.data.entity.user.Contactor
import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.Teacher
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.data.repository.token.CheckEmailCodeRepository
import com.info.info_v1_backend.domain.auth.data.repository.token.RefreshTokenRepository
import com.info.info_v1_backend.domain.auth.data.repository.user.StudentRepository
import com.info.info_v1_backend.domain.auth.data.repository.user.UserRepository
import com.info.info_v1_backend.domain.auth.exception.*
import com.info.info_v1_backend.domain.auth.presentation.dto.request.*
import com.info.info_v1_backend.domain.auth.presentation.dto.response.*
import com.info.info_v1_backend.global.security.jwt.TokenProvider
import com.info.info_v1_backend.global.security.jwt.data.TokenResponse
import com.info.info_v1_backend.global.security.jwt.exception.ExpiredTokenException
import com.info.info_v1_backend.global.util.user.CurrentUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val checkEmailCodeRepository: CheckEmailCodeRepository,
    private val userRepository: UserRepository<User>,
    private val studentRepository: StudentRepository,
    private val tokenProvider: TokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val current: CurrentUtil,

    ) : AuthService {
    override fun studentSignUp(req: StudentSignUpRequest) {
        if (checkEmail(req.email, req.emailCheckCode)) {
            val encPw = passwordEncoder.encode(req.password)
            val user = Student(
                    req.studentKey,
                    req.name,
                    req.email,
                    encPw,
                    req.githubLink,
                    creationList = null
            )
            userRepository.save(user)
        } else throw CheckEmailCodeException(req.emailCheckCode)
    }

    override fun teacherSignUp(req: TeacherSingUpRequest) {
        if (checkEmail(req.email, req.emailCheckCode)) {
            if (req.teacherCheckCode == "1111") {
                val encPw = passwordEncoder.encode(req.password)

                val user = Teacher(
                        req.name,
                        req.email,
                        encPw
                )
                userRepository.save(user)
            } else throw CheckTeacherCodeException(req.teacherCheckCode)
        } else throw CheckEmailCodeException(req.emailCheckCode)
    }

    override fun contactorSignup(req: ContactorSignupRequest) {
        if (checkEmail(req.email, req.emailCheckCode)) {
            if (req.contactorCheckCode == "1111") {
                val encPw = passwordEncoder.encode(req.password)

                userRepository.save(
                        Contactor(
                                req.name,
                                req.position,
                                req.personalPhone,
                                req.email,
                                encPw
                        )
                )
            } else throw CheckContactorCodeException(req.contactorCheckCode)

        }
    }

    private fun checkEmail(email: String, authCode: String): Boolean {
        userRepository.findByEmail(email).orElse(null)?.let {
            throw UserAlreadyExists(email)
        }
        if ((checkEmailCodeRepository.findById(email).orElse(null)
                        ?: throw CheckEmailCodeException(email)).code == authCode) {
            return true
        }
        return false
    }

    override fun login(req: LoginRequest): TokenResponse {
        val user = userRepository.findByEmail(req.email)
                .orElse(null) ?: throw UserNotFoundException(req.email)
        if (passwordEncoder.matches(req.password, user.password)) {
            return tokenProvider.encode(user.id.toString())
        } else throw IncorrectPassword(req.password)
    }

    override fun editPassword(req: EditPasswordRequest) {
        val user = current.getCurrentUser()
        if ((checkEmailCodeRepository.findById(user.email).orElse(null)
                        ?: throw CheckEmailCodeException(user.email)).code == req.code) {
            val encPw = passwordEncoder.encode(req.password)
            user.editPassword(encPw)
        } else throw CheckPasswordCodeException(req.code)
    }

    override fun reissue(req: ReissueRequest): TokenResponse {
        if (tokenProvider.isExpired(req.refreshToken)) throw ExpiredTokenException(req.refreshToken)

        val userId = tokenProvider.decodeBody(req.accessToken).subject
        val user = userRepository.findById(userId.toLong()).orElse(null) ?: throw UserNotFoundException(userId)
        val tokenResponse = tokenProvider.encode(user.id.toString())
        val token = RefreshToken(user.id.toString(), tokenResponse.refreshToken)

        refreshTokenRepository.findById(user.id.toString()).map {
            it.reset(token.token)
        }.orElse(null) ?: refreshTokenRepository.save(token)
        return tokenResponse
    }

    override fun deleteMe() {
        userRepository.delete(current.getCurrentUser())
    }

    override fun getUserInfo(email: String?): GetUserInfo {
        val email = email ?: current.getCurrentUser().email
        val user = userRepository.findByEmail(email).orElse(null) ?: throw UserNotFoundException(email)
        return when (user) {
            is Student -> {
                GetStudentInfo(
                        user.name,
                        user.studentKey,
                        user.email,
                        user.githubLink,
                        user.isHired,
                        user.creationList?.map {
                            it.project!!.toProjectList()
                        },
                        user.company?.toMinimumCompanyResponse()
                )
            }

            is Teacher -> {
                GetTeacherInfo(
                        user.name,
                        user.email
                )
            }

            is Contactor -> {
                GetContactor(
                        user.name,
                        user.email,
                        user.position,
                        user.personalPhone,
                        user.company?.toMinimumCompanyResponse()
                )
            }
            else -> {
                throw UserNotFoundException(user.email)
            }
        }
    }

    override fun editMyInfo(request: EditMyInfo) {
        val current = current.getCurrentUser()

        if (current is Student) {
            val user = studentRepository.findById(current.id!!.toLong()).orElse(null)
                    ?: throw UserNotFoundException(current.id.toString())
            user.editMyInfo(request)
        } else throw IsNotStudentException(current.roleList.toString())
    }

    override fun getStudentList(): MinimumStudentList {
        val list =  studentRepository.findAll().map {
            it.toMinimumStudent()
        }
        return MinimumStudentList(list)
    }

    override fun changeEmail(request: ChangeEmailRequest) {
        val current = current.getCurrentUser()
        if (current !is Student) {
            if (passwordEncoder.matches(request.password, current.password)) {
                current.changeEmail(request.email)
            } else throw IncorrectPassword(request.password)
        } else throw IsNotContactorOrTeacher(current.roleList.toString())
    }
}