package com.info.info_v1_backend.domain.auth.presentation.controller

import com.info.info_v1_backend.domain.auth.business.dto.request.*
import com.info.info_v1_backend.domain.auth.business.dto.response.MinimumStudent
import com.info.info_v1_backend.domain.auth.business.service.AuthService
import com.info.info_v1_backend.domain.auth.business.service.EmailService
import com.info.info_v1_backend.domain.auth.business.dto.response.UserInfoResponse
import com.info.info_v1_backend.domain.auth.business.service.UserService
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.exception.CheckEmailCodeException
import com.info.info_v1_backend.domain.auth.exception.CheckTeacherCodeException
import com.info.info_v1_backend.domain.auth.exception.UserNotFoundException
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyIntroductionRequest
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.global.security.jwt.data.TokenResponse
import com.info.info_v1_backend.global.util.user.CurrentUtil
import org.springframework.data.domain.Page
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/api/info/v1/auth")
@Validated
class AuthController(
    private val authService: AuthService,
    private val emailService: EmailService,
) {
    @PostMapping("/email/school")
    fun sendSchoolEmail(
        @Valid
        @RequestParam
        @Pattern(regexp = "[a-zA-Z\\d+_.]+@dsm\\.hs\\.kr$", message = "올바른 이메일 형식이 아닙니다.")
        email: String
    ){
        emailService.sendCodeToEmail(email)
    }

    @PostMapping("/signup/student")
    fun studentSignup(
        @Valid
        @RequestBody
        request: StudentSignUpRequest
    ){
        if (authService.checkEmail(request.email, request.emailCheckCode)) {
            authService.studentSignUp(request)
        } else throw CheckEmailCodeException(request.emailCheckCode)
    }

    @PostMapping("/signup/teacher")
    fun teacherSignup(
        @Valid
        @RequestBody
        request: TeacherSingUpRequest
    ){
        if (authService.checkEmail(request.email, request.emailCheckCode)) {
            if (authService.checkTeacherCode(request.teacherCheckCode)) {
                authService.teacherSignUp(request)
            } else throw CheckTeacherCodeException(request.teacherCheckCode)
        } else throw CheckEmailCodeException(request.emailCheckCode)
    }



    @PostMapping("/password/code")
    fun sendPasswordCode(
        @AuthenticationPrincipal user: User?
    ){
        emailService.sendPasswordCodeToEmail(
            (user?: throw UserNotFoundException("No User Found")).email
        )
    }

    @PostMapping("/password")
    fun changePassword(
        @RequestBody request: EditPasswordRequest,
        @AuthenticationPrincipal user: User?
    ){
        authService.changePassword(
            user?: throw UserNotFoundException("No User Found"),
            request
        )
    }


    @PostMapping("/login")
    fun login(
        @RequestBody
        request: LoginRequest
    ): TokenResponse{
        return authService.login(request)
    }


    @PostMapping("/reissue")
    fun reissue(
        @RequestBody
        request: ReissueRequest
    ): TokenResponse{
       return authService.reissue(request)
    }


//    @DeleteMapping("/me")
//    fun deleteMe(@AuthenticationPrincipal user: User?) {
//
//        authService.deleteMe(user?: throw UserNotFoundException(""))
//    }




    @PatchMapping("/email")
    fun changeEmail(
        @Valid
        @RequestBody request: ChangeEmailRequest,
        @AuthenticationPrincipal user: User?
    ){
        authService.changeEmail(user?: throw UserNotFoundException("No User Found"), request)
    }
}