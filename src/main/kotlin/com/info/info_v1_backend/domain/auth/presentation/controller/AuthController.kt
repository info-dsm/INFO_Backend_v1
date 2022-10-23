package com.info.info_v1_backend.domain.auth.presentation.controller

import com.info.info_v1_backend.domain.auth.business.dto.request.*
import com.info.info_v1_backend.domain.auth.business.service.AuthService
import com.info.info_v1_backend.domain.auth.business.service.EmailService
import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.Teacher
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.exception.CheckEmailCodeException
import com.info.info_v1_backend.domain.auth.exception.CheckTeacherCodeException
import com.info.info_v1_backend.domain.auth.exception.IncorrectEmail
import com.info.info_v1_backend.domain.auth.exception.UserNotFoundException
import com.info.info_v1_backend.domain.company.exception.AlreadyExistsException
import com.info.info_v1_backend.global.error.common.NoAuthenticationException
import com.info.info_v1_backend.global.error.common.TokenCanNotBeNullException
import com.info.info_v1_backend.global.security.jwt.data.TokenResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/api/info/v1/auth")
@Validated
class AuthController(
    private val authService: AuthService,
    private val emailService: EmailService,
) {

    @GetMapping("/email/code/check")
    fun checkSchoolEmailCode(
        @RequestParam(required =  true) email: String,
        @RequestParam(required = true) code: String
    ) {
        if (!authService.checkSchoolEmailCode(email, code)) throw CheckEmailCodeException("$email $code")
    }

    @GetMapping("/student/check")
    fun studentKeyCheck(
        @RequestParam(required = true) studentKey: String
    ) {
        if (!authService.checkStudentKey(studentKey)) throw AlreadyExistsException(studentKey)
    }


    @PostMapping("/email/school")
    @ResponseStatus(HttpStatus.CREATED)
    fun sendSchoolEmail(
        @Valid
        @RequestParam
        @Pattern(regexp = "[a-zA-Z\\d+_.]+@dsm\\.hs\\.kr$", message = "올바른 이메일 형식이 아닙니다.")
        email: String
    ){
        emailService.sendCodeToSchoolEmail(email)
    }

    @PostMapping("/signup/student")
    @ResponseStatus(HttpStatus.CREATED)
    fun studentSignup(
        @Valid
        @RequestBody
        request: StudentSignUpRequest
    ){
        if (authService.checkSchoolEmailAndDeleteCode(request.email, request.emailCheckCode)) {
            authService.studentSignUp(request)
        } else throw CheckEmailCodeException(request.emailCheckCode)
    }

    @PostMapping("/signup/teacher")
    @ResponseStatus(HttpStatus.CREATED)
    fun teacherSignup(
        @Valid
        @RequestBody
        request: TeacherSingUpRequest
    ){
        if (authService.checkSchoolEmailAndDeleteCode(request.email, request.emailCheckCode)) {
            if (authService.checkTeacherCode(request.teacherCheckCode)) {
                authService.teacherSignUp(request)
            } else throw CheckTeacherCodeException(request.teacherCheckCode)
        } else throw CheckEmailCodeException(request.emailCheckCode)
    }


    @PostMapping("/password/code")
    @ResponseStatus(HttpStatus.CREATED)
    fun sendPasswordCode(
        @RequestParam(required = true) email: String
    ){
        emailService.sendPasswordCodeToEmail(email)
    }

    @PostMapping("/password")
    fun changePassword(
        @Valid
        @RequestBody request: EditPasswordRequest,
        @AuthenticationPrincipal user: User?
    ){
        authService.changePassword(
            user?: throw UserNotFoundException("No User Found"),
            request
        )
    }


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    fun login(
        @RequestBody
        request: LoginRequest
    ): TokenResponse{
        return authService.login(request)
    }


    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.CREATED)
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

    @PostMapping("/email")
    fun sendChangeEmail(
        @AuthenticationPrincipal
        user: User?,
        @RequestParam
        @Email
        email: String
    ) {
        if ((user ?: throw TokenCanNotBeNullException()) !is Student) {
            if (user is Teacher) {
                if (email.endsWith("@dsm.hs.kr")) {
                    emailService.sendChangeEmailCodeToEmail(email)
                } else throw IncorrectEmail(email)
            }
            else emailService.sendChangeEmailCodeToEmail(email)
        } else throw NoAuthenticationException(user.roleList.toString())
    }
    @PatchMapping("/email")
    fun changeEmail(
        @Valid
        @RequestBody request: ChangeEmailRequest,
        @AuthenticationPrincipal user: User?
    ){
        authService.changeEmail(user?: throw TokenCanNotBeNullException(), request)
    }
}