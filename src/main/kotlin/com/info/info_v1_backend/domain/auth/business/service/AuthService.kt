package com.info.info_v1_backend.domain.auth.business.service

import com.info.info_v1_backend.domain.auth.presentation.dto.request.EditPasswordRequest
import com.info.info_v1_backend.domain.auth.presentation.dto.request.LoginRequest
import com.info.info_v1_backend.domain.auth.presentation.dto.request.StudentSignUpRequest
import com.info.info_v1_backend.domain.auth.presentation.dto.request.TeacherSingUpRequest
import com.info.info_v1_backend.global.security.jwt.data.TokenResponse

interface AuthService {
    fun studentSignUp(req: StudentSignUpRequest): TokenResponse
    fun teacherSignUp(req: TeacherSingUpRequest): TokenResponse
    fun login(req: LoginRequest): TokenResponse
    fun editPassword(req: EditPasswordRequest)
}