package com.info.info_v1_backend.domain.auth.business.service

import com.info.info_v1_backend.domain.auth.presentation.dto.request.*
import com.info.info_v1_backend.global.security.jwt.data.TokenResponse

interface AuthService {
    fun studentSignUp(req: StudentSignUpRequest)
    fun teacherSignUp(req: TeacherSingUpRequest)

    fun contactorSignup(req: ContactorSignupRequest)
    fun login(req: LoginRequest): TokenResponse
    fun editPassword(req: EditPasswordRequest)
    fun reissue(req: ReissueRequest): TokenResponse

}