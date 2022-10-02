package com.info.info_v1_backend.domain.auth.business.service

import com.info.info_v1_backend.domain.auth.business.dto.request.*
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyIntroductionRequest
import com.info.info_v1_backend.global.security.jwt.data.TokenResponse


interface AuthService {
    fun studentSignUp(req: StudentSignUpRequest)
    fun teacherSignUp(req: TeacherSingUpRequest)
    fun companySignup(req: CompanySignupRequest, emailCheckCode: String, companyIntroduction: CompanyIntroductionRequest)

    fun changePassword(user: User, req: EditPasswordRequest)

    fun login(req: LoginRequest): TokenResponse
    fun reissue(req: ReissueRequest): TokenResponse

    fun changeEmail(user: User, request: ChangeEmailRequest)

}