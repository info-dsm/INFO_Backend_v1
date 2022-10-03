package com.info.info_v1_backend.domain.auth.presentation.controller

import com.info.info_v1_backend.domain.auth.business.dto.request.*
import com.info.info_v1_backend.domain.auth.business.dto.response.MinimumStudent
import com.info.info_v1_backend.domain.auth.business.service.AuthService
import com.info.info_v1_backend.domain.auth.business.service.EmailService
import com.info.info_v1_backend.domain.auth.business.dto.response.UserInfoResponse
import com.info.info_v1_backend.domain.auth.business.service.UserService
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.exception.UserNotFoundException
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyIntroductionRequest
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.global.security.jwt.data.TokenResponse
import com.info.info_v1_backend.global.util.user.CurrentUtil
import org.springframework.data.domain.Page
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/api/info/v1/auth")
@Validated
class AuthController(
    private val authService: AuthService,
    private val emailService: EmailService,
    private val userService: UserService
) {
    @PostMapping("/email/school")
    fun sendSchoolEmail(
        @RequestParam
        @Pattern(regexp = "[a-zA-Z\\d+_.]+@dsm\\.hs\\.kr$", message = "올바른 이메일 형식이 아닙니다.")
        email: String
    ){
        emailService.sendCodeToEmail(email)
    }

    @PostMapping("/email/company")
    fun sendCompanyEmail(
        @RequestParam
        email: String
    ){
        emailService.sendCodeToEmail(email)
    }

    @PostMapping("/signup/student")
    fun studentSignup(
        @RequestBody @Valid
        request: StudentSignUpRequest
    ){
        authService.studentSignUp(request)
    }

    @PostMapping("/signup/teacher")
    fun teacherSignup(
        @RequestBody @Valid
        request: TeacherSingUpRequest
    ){
        authService.teacherSignUp(request)
    }

    @PostMapping("/signup/company")
    fun companySignup(
        @RequestBody request: CompanySignupRequest,
        @RequestParam emailCheckCode: String,
        @RequestPart businessRegisteredCertificate: MultipartFile,
        @RequestPart companyIntroductionFile: List<MultipartFile>,
        @RequestPart companyLogo: MultipartFile,
        @RequestPart companyPhotoList: List<MultipartFile>
    ) {
        authService.companySignup(
            request,
            emailCheckCode,
            CompanyIntroductionRequest(
                businessRegisteredCertificate,
                companyIntroductionFile,
                companyLogo,
                companyPhotoList
            )
        )
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




    @PostMapping("/changeEmail")
    fun changeEmail(
            @RequestBody request: ChangeEmailRequest,
            @AuthenticationPrincipal user: User?
    ){
        authService.changeEmail(user?: throw UserNotFoundException("No User Found"), request)
    }
}