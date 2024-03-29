package com.info.info_v1_backend.domain.auth.business.service

import com.info.info_v1_backend.domain.auth.business.dto.request.*
import com.info.info_v1_backend.domain.auth.data.entity.token.CheckEmailCode
import com.info.info_v1_backend.domain.auth.data.entity.token.RefreshToken
import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.Teacher
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.data.repository.token.*
import com.info.info_v1_backend.domain.auth.data.repository.user.StudentRepository
import com.info.info_v1_backend.domain.auth.data.repository.user.UserRepository
import com.info.info_v1_backend.domain.auth.exception.*
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyIntroductionRequest
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.company.CompanySearchDocument
import com.info.info_v1_backend.domain.company.data.entity.company.embeddable.CompanyIntroduction
import com.info.info_v1_backend.domain.company.data.entity.company.file.BusinessRegisteredCertificateFile
import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyIntroductionFile
import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyLogoFile
import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyPhotoFile
import com.info.info_v1_backend.domain.company.data.entity.company.tag.BusinessArea
import com.info.info_v1_backend.domain.company.data.entity.company.tag.BusinessAreaTagged
import com.info.info_v1_backend.domain.company.data.repository.company.*
import com.info.info_v1_backend.global.error.common.NoAuthenticationException
import com.info.info_v1_backend.global.error.common.TokenCanNotBeNullException
import com.info.info_v1_backend.global.file.repository.FileRepository
import com.info.info_v1_backend.global.security.jwt.TokenProvider
import com.info.info_v1_backend.global.security.jwt.data.TokenResponse
import com.info.info_v1_backend.global.security.jwt.exception.ExpiredTokenException
import com.info.info_v1_backend.infra.amazon.s3.S3Util
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Async
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class AuthServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val checkEmailCodeRepository: CheckEmailCodeRepository,
    private val userRepository: UserRepository<User>,
    private val tokenProvider: TokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val checkSchoolEmailCodeRepository: CheckSchoolEmailCodeRepository,
    private val checkCompanyEmailCodeRepository: CheckCompanyEmailCodeRepository,
    private val checkPasswordCodeRepository: CheckPasswordCodeRepository,
    private val studentRepository: StudentRepository
): AuthService {

    @Async
    override fun studentSignUp(req: StudentSignUpRequest) {
        val encPw = passwordEncoder.encode(req.password)
        val user = Student(
            req.studentKey,
            req.name,
            req.email,
            encPw,
            creationList = null,
            req.githubLink
        )
        userRepository.save(user)

    }

    @Async
    override fun teacherSignUp(req: TeacherSingUpRequest) {
        val encPw = passwordEncoder.encode(req.password)

        val user = Teacher(
                req.name,
                req.email,
                encPw
        )
        userRepository.save(user)

    }

    override fun checkTeacherCode(code: String): Boolean {
        return code == "1111"
    }



    override fun checkSchoolEmailAndDeleteCode(email: String, authCode: String): Boolean {
        checkExistsEmail(email)
        val checkEmail = checkSchoolEmailCodeRepository.findByIdOrNull(email)
        if ((checkEmail?: throw CheckEmailCodeException(email)).code == authCode) {
            checkSchoolEmailCodeRepository.delete(checkEmail)
            return true
        }
        return false
    }

    override fun checkSchoolEmailCode(email: String, authCode: String): Boolean {
        checkExistsEmail(email)
        val checkEmail = checkSchoolEmailCodeRepository.findByIdOrNull(email)
        if ((checkEmail?: throw CheckEmailCodeException(email)).code == authCode) {
            return true
        }
        return false
    }

    override fun checkCompanyEmailAndDeleteCode(email: String, authCode: String): Boolean {
        checkExistsEmail(email)
        val checkEmail = checkCompanyEmailCodeRepository.findByIdOrNull(email)
        if ((checkEmail?: throw CheckEmailCodeException(email)).code == authCode) {
            checkCompanyEmailCodeRepository.delete(checkEmail)
            return true
        }
        return false
    }

    override fun checkCompanyEmailCode(email: String, authCode: String): Boolean {
        checkExistsEmail(email)
        val checkEmail = checkCompanyEmailCodeRepository.findByIdOrNull(email)
        if ((checkEmail?: throw CheckEmailCodeException(email)).code == authCode) {
            return true
        }
        return false
    }

    override fun checkStudentKey(studentKey: String): Boolean {
        studentRepository.findByStudentKey(studentKey).orElse(null)
            ?: return false
        return true
    }

    override fun login(req: LoginRequest): TokenResponse {
        val user = userRepository.findByEmail(req.email)
                .orElse(null) ?: throw UserNotFoundException(req.email)
        if (passwordEncoder.matches(req.password, user.password)) {
            val response = tokenProvider.encode(user.id.toString())
            refreshTokenRepository.save(
            refreshTokenRepository.findByIdOrNull(user.id.toString())
                ?.reset(response.refreshToken)
                ?: RefreshToken(
                        user.id.toString(),
                        response.refreshToken
                    )
            )
            return response
        } else throw IncorrectPassword(req.password)
    }

    override fun changePassword(user: User, req: EditPasswordRequest) {
        val passwordCode = checkPasswordCodeRepository.findByIdOrNull(user.email)
        if ((passwordCode?: throw CheckEmailCodeException(user.email)).code == req.code) {
            val encPw = passwordEncoder.encode(req.password)
            user.editPassword(encPw)
            checkPasswordCodeRepository.delete(passwordCode)
        } else throw CheckPasswordCodeException(req.code)
    }

    override fun reissue(req: ReissueRequest): TokenResponse {
        val userId = tokenProvider.getSubjectWithExpiredCheck(req.accessToken)
        val refreshToken = refreshTokenRepository.findByIdOrNull(userId)?: throw TokenCanNotBeNullException()
        if (refreshToken.token != req.refreshToken) throw ExpiredTokenException(req.refreshToken)

        val tokenResponse = tokenProvider.encode(userId)
        val token = RefreshToken(userId, tokenResponse.refreshToken)

        refreshTokenRepository.save(
            refreshTokenRepository.findById(userId).map {
                it.reset(token.token)
            }.orElse(null) ?: (token)
        ).token

        return tokenResponse
    }



    override fun changeEmail(user: User, request: ChangeEmailRequest) {
        checkExistsEmail(request.email)
        val changeEmailCode = checkEmailCodeRepository.findByIdOrNull(request.email)

        when(user) {
            is Student -> throw NoAuthenticationException(user.roleList.toString())

            is Teacher ->
                if ((changeEmailCode ?: throw CheckEmailCodeException(request.email)).code == request.code) {
                    if (request.email.endsWith("@dsm.hs.kr")) {
                        if (passwordEncoder.matches(request.password, user.password)) {
                            user.changeEmail(request.email)
                            checkEmailCodeRepository.delete(changeEmailCode)
                        } else throw IncorrectPassword(request.password)
                    } else throw IncorrectEmail(request.email)
                } else throw CheckEmailCodeException(request.code)

            is Company ->
                if ((changeEmailCode ?: throw CheckEmailCodeException(request.email)).code == request.code) {
                    if (passwordEncoder.matches(request.password, user.password)) {
                        user.changeEmail(request.email)
                        checkEmailCodeRepository.delete(changeEmailCode)
                    } else throw IncorrectPassword(request.password)
                } else throw CheckEmailCodeException(request.code)
        }
    }

    private fun checkExistsEmail(email: String) {
        userRepository.findByEmail(email).orElse(null)?.let {
            throw UserAlreadyExists(email)
        }
    }


}