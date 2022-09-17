package com.info.info_v1_backend.domain.company.presentation

import com.info.info_v1_backend.domain.company.business.service.CompanyService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/info/v1/company")
class CompanyController(
    private val companyService: CompanyService
) {


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerCompany() {
        companyService.registerCompany()
    }


}