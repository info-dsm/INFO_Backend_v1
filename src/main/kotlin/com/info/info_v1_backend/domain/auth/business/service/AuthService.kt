package com.info.info_v1_backend.domain.auth.business.service

import com.info.info_v1_backend.domain.auth.presentation.dto.request.StudentSignUpRequest
import com.info.info_v1_backend.global.security.jwt.data.TokenResponse

interface AuthService {
    fun studentSignUp(req: StudentSignUpRequest): TokenResponse
}