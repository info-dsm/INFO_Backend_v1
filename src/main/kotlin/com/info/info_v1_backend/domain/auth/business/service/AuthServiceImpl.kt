package com.info.info_v1_backend.domain.auth.business.service

import com.info.info_v1_backend.domain.auth.business.dto.request.*
import com.info.info_v1_backend.domain.auth.data.entity.token.RefreshToken
import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.Teacher
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.data.repository.token.CheckEmailCodeRepository
import com.info.info_v1_backend.domain.auth.data.repository.token.RefreshTokenRepository
import com.info.info_v1_backend.domain.auth.data.repository.user.UserRepository
import com.info.info_v1_backend.domain.auth.exception.*
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.company.CompanySearchDocument
import com.info.info_v1_backend.domain.company.data.entity.company.embeddable.CompanyIntroduction
import com.info.info_v1_backend.domain.company.data.entity.company.file.BusinessRegisteredCertificateFile
import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyIntroductionFile
import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyLogoFile
import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyPhotoFile
import com.info.info_v1_backend.domain.company.data.repository.company.CompanyRepository
import com.info.info_v1_backend.domain.company.data.repository.company.CompanySearchDocumentRepository
import com.info.info_v1_backend.global.error.common.NoAuthenticationException
import com.info.info_v1_backend.global.file.repository.FileRepository
import com.info.info_v1_backend.global.security.jwt.TokenProvider
import com.info.info_v1_backend.global.security.jwt.data.TokenResponse
import com.info.info_v1_backend.global.security.jwt.exception.ExpiredTokenException
import com.info.info_v1_backend.infra.amazon.s3.S3Util
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val checkEmailCodeRepository: CheckEmailCodeRepository,
    private val userRepository: UserRepository<User>,
    private val s3Util: S3Util,
    private val companySearchDocumentRepository: CompanySearchDocumentRepository,
    private val businessRegisteredCertificateFileRepository: FileRepository<BusinessRegisteredCertificateFile>,
    private val companyIntroductionFileRepository: FileRepository<CompanyIntroductionFile>,
    private val companyPhotoFileRepository: FileRepository<CompanyPhotoFile>,
    private val companyRepository: CompanyRepository,
    private val tokenProvider: TokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
): AuthService {

    override fun studentSignUp(req: StudentSignUpRequest) {
        if (checkEmail(req.email, req.emailCheckCode)) {
            val encPw = passwordEncoder.encode(req.password)
            val user = Student(
                    req.studentKey,
                    req.name,
                    req.email,
                    encPw,
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

    override fun companySignup(req: CompanySignupRequest, emailCheckCode: String) {
        if (checkEmail(req.companyContact.email, emailCheckCode)) {

            val company = companyRepository.save(
                Company(
                    passwordEncoder.encode(req.password),
                    req.companyNameRequest,
                    req.companyInformation.toCompanyInformation(),
                    req.companyContact.toCompanyContact(),
                    CompanyIntroduction(
                        req.companyIntroduction.introduction
                    ),
                    req.isLeading
                )
            )
            req.companyIntroduction.businessRegisteredCertificate.let{
                businessRegisteredCertificateFileRepository.save(
                    BusinessRegisteredCertificateFile(
                        s3Util.uploadFile(it, "company", "businessRegisteredCertificate"),
                        company
                    )
                )
            }

            req.companyIntroduction.companyIntroductionFile.map {
                companyIntroductionFileRepository.save(
                    CompanyIntroductionFile(
                        s3Util.uploadFile(it, "company", "companyIntroduction"),
                        company
                    )
                )
            }

            req.companyIntroduction.companyLogo?.let {
                companyIntroductionFileRepository.save(
                    CompanyIntroductionFile(
                        s3Util.uploadFile(it, "company", "companyLogo"),
                        company
                    )
                )
            }

            req.companyIntroduction.companyPhotoList.map {
                companyPhotoFileRepository.save(
                    CompanyPhotoFile(
                        s3Util.uploadFile(it, "company", "companyPhoto"),
                        company
                    )
                )
            }
            companySearchDocumentRepository.save(
                CompanySearchDocument(
                    company.name,
                    company.id!!,
                )
            )

        } else throw CheckEmailCodeException(emailCheckCode)
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

    override fun changePassword(user: User, req: EditPasswordRequest) {
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



    override fun changeEmail(user: User, request: ChangeEmailRequest) {
        if (user !is Student) {
            if (passwordEncoder.matches(request.password, user.password)) {
                user.changeEmail(request.email)
            } else throw IncorrectPassword(request.password)
        } else throw NoAuthenticationException(user.roleList.toString())
    }
}