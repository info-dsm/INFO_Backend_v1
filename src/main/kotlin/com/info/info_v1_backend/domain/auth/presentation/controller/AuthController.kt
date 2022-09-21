package com.info.info_v1_backend.domain.auth.presentation.controller

import com.info.info_v1_backend.domain.auth.business.service.AuthService
import com.info.info_v1_backend.domain.auth.business.service.EmailService
import com.info.info_v1_backend.domain.auth.presentation.dto.request.*
import com.info.info_v1_backend.domain.auth.presentation.dto.response.GetUserInfo
import com.info.info_v1_backend.domain.auth.presentation.dto.response.MinimumStudentList
import com.info.info_v1_backend.global.security.jwt.data.TokenResponse
import com.info.info_v1_backend.global.util.user.CurrentUtil
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/api/info/v1/auth")
@Validated
class AuthController(
    private val authService: AuthService,
    private val emailService: EmailService,
    private val current: CurrentUtil,
) {
    @PostMapping("/email")
    fun sendEmail(
        @RequestParam
        @Pattern(regexp = "[a-zA-Z0-9+\\_.]+@dsm\\.hs\\.kr\$", message = "이메일 형식이 맞지 않습니다.")
        email: String
    ){
        emailService.sendCodeToEmail(email)
    }

    @PostMapping("/passwordCode")
    fun sendPasswordCode(){
        emailService.sendCodeToEmail(current.getCurrentUser().email)
    }

    @PostMapping("/studentSignUp")
    fun studentSignUp(
        @RequestBody @Valid
        request: StudentSignUpRequest
    ){
        authService.studentSignUp(request)
    }

    @PostMapping("/teacherSingUp")
    fun teacherSignUp(
        @RequestBody @Valid
        request: TeacherSingUpRequest
    ){
        authService.teacherSignUp(request)
    }

    @PostMapping("/changePassword")
    fun password(
        @RequestBody
        request: EditPasswordRequest
    ){
        authService.editPassword(request)
    }
    @PostMapping("/passwordCode")
    fun sendPasswordCode(){
        emailService.sendPasswordCodeToEmail(current.getCurrentUser().email)
    }

    @PostMapping("/reissue")
    fun reissue(
        @RequestBody
        request: ReissueRequest
    ): TokenResponse{
       return authService.reissue(request)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody
        request: LoginRequest
    ): TokenResponse{
        return authService.login(request)
    }

    @DeleteMapping("/me")
    fun deleteMe() {
        authService.deleteMe()
    }
    @PatchMapping("/me")
    fun editMyInfo(
            @RequestBody
            request: EditMyInfo
    ){
        authService.editMyInfo(request)
    }
    @GetMapping("/me")
    fun getStudentInfo(
            @RequestParam(required = false)
            name: String?
    ): GetUserInfo {
        return authService.getUserInfo(name)
    }

    @PostMapping("/getStudentList")
    fun getStudentList(): MinimumStudentList{
        return authService.getStudentList()
    }
    @PostMapping("/changeEmail")
    fun changeEmail(
            @RequestBody
            req: ChangeEmailRequest
    ){
        authService.changeEmail(req)
    }
}