package com.info.info_v1_backend.domain.auth.presentation.controller

import com.info.info_v1_backend.domain.auth.business.service.AuthService
import com.info.info_v1_backend.domain.auth.business.service.EmailService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authService: AuthService,
    private val emailService: EmailService
) {
    @PostMapping("/email")
    fun sendEmail(
        @RequestParam
        @Pattern(regexp = "[a-zA-Z0-9+\\_.]+@dsm\\.hs\\.kr\$")
        email: String){
        emailService.sendCodeToEmail(email)
    }

    @PostMapping("/studentSignup")
    fun studentSignup(){

    }

}