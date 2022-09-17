package com.info.info_v1_backend.domain.company.presentation

import com.info.info_v1_backend.domain.auth.business.service.EmailService
import com.info.info_v1_backend.domain.company.business.dto.request.company.EditCompanyRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.RegisterCompanyRequest
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse
import com.info.info_v1_backend.domain.company.business.service.CompanyService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
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

    @PatchMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun editCompany(
        @RequestBody request: EditCompanyRequest,
        @RequestParam id: Long
    ) {
        companyService.editCompany(request, id)
    }

    @GetMapping("/list")
    fun getMinimumCompanyList(
        @RequestParam(defaultValue = "0") idx: Int,
        @RequestParam size: Int
    ): Page<MinimumCompanyResponse> {
        return companyService.getMinimumCompanyList(idx, size)
    }

    @GetMapping
    fun getMaximumCompany(
        @RequestParam id: Long
    ): MaximumCompanyResponse {
        return companyService.getMaximumCompany(id)
    }

    @GetMapping("/user")
    fun getMaximumCompanyByUserId(@RequestParam id: Long): List<MaximumCompanyResponse> {
        return companyService.getMaximumCompanyByUserId(id)
    }

    @GetMapping("/search")
    fun searchCompany(@RequestParam query: String): List<MinimumCompanyResponse> {
        return companyService.searchCompany(query)
    }


}