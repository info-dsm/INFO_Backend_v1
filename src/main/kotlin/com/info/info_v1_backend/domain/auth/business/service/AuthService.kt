package com.info.info_v1_backend.domain.auth.business.service

import com.info.info_v1_backend.domain.auth.business.dto.request.*
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyIntroductionRequest
import com.info.info_v1_backend.global.security.jwt.data.TokenResponse
import org.springframework.web.multipart.MultipartFile


interface AuthService {
    fun studentSignUp(req: StudentSignUpRequest)

    fun teacherSignUp(req: TeacherSingUpRequest)
    fun checkTeacherCode(code: String): Boolean

    fun companySignup(req: CompanySignupRequest, emailCheckCode: String, businessRegisteredCertificate: MultipartFile, companyIntroductionFile: List<MultipartFile>, companyLogo: MultipartFile, companyPhotoList: List<MultipartFile>)

    fun checkEmail(email: String, authCode: String): Boolean

    fun changePassword(user: User, req: EditPasswordRequest)

    fun login(req: LoginRequest): TokenResponse
    fun reissue(req: ReissueRequest): TokenResponse

    fun changeEmail(user: User, request: ChangeEmailRequest)

}