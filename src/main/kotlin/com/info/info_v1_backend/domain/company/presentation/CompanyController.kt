package com.info.info_v1_backend.domain.company.presentation

import com.info.info_v1_backend.domain.auth.business.service.AuthService
import com.info.info_v1_backend.domain.auth.business.service.EmailService
import com.info.info_v1_backend.domain.company.business.dto.request.RegisterCompanyRequest
import com.info.info_v1_backend.domain.company.business.service.CompanyService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/info/v1/company")
@Validated
class CompanyController(
    private val companyService: CompanyService,
    private val emailService: EmailService
) {

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerCompany(@RequestBody request: RegisterCompanyRequest) {
        companyService.registerCompany(request)
    }


}