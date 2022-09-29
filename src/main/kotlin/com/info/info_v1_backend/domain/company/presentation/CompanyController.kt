package com.info.info_v1_backend.domain.company.presentation

import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.company.EditCompanyRequest
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse
import com.info.info_v1_backend.domain.company.business.service.CompanyService
import com.info.info_v1_backend.global.error.common.TokenNotFoundException
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

@RestController
@RequestMapping("/api/info/v1/company")
@Validated
class CompanyController(
    private val companyService: CompanyService,
) {

    @PatchMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun editCompany(
        @AuthenticationPrincipal user: User?,
        @RequestBody request: EditCompanyRequest,
        @RequestParam(required = true) id: Long
    ) {
        companyService.editCompany(user?: throw TokenNotFoundException(), request)
    }

    @GetMapping("/list")
    fun getMinimumCompanyList(
        @RequestParam(defaultValue = "0") idx: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<MinimumCompanyResponse> {
        return companyService.getMinimumCompanyList(idx, size)
    }

    @GetMapping
    fun getMaximumCompany(
        @RequestParam(required = true) id: Long
    ): MaximumCompanyResponse {
        return companyService.getMaximumCompany(id)
    }

    @GetMapping("/user")
    fun getMaximumCompanyByUserId(
        @AuthenticationPrincipal user: User?,
        @RequestParam(required = true) id: Long
    ): List<MaximumCompanyResponse> {
        return companyService.getEntireMaximumCompanyByUserId(user?: throw TokenNotFoundException(), id)
    }

    @GetMapping("/search")
    fun searchCompany(
        @RequestParam(required = true) query: String
    ): List<MinimumCompanyResponse> {
        return companyService.searchCompany(query)
    }


    
    @PutMapping("/certificate")
    fun changeBusinessRegisteredCertificate(
        @AuthenticationPrincipal user: User?, 
        @RequestPart certificate: MultipartFile
    ) {
        companyService.changeBusinessRegisteredCertificate(user?: throw TokenNotFoundException(), certificate)
    }

    @PutMapping("/introduction")
    fun addCompanyIntroductionFile(
        @AuthenticationPrincipal user: User?, 
        @RequestPart introduction: MultipartFile
    ) {
        companyService.addCompanyIntroductionFile(user?: throw TokenNotFoundException(), introduction)
    }

    @DeleteMapping("/introduction")
    fun removeCompanyIntroductionFile(
        @AuthenticationPrincipal user: User?,
        @RequestParam(required = true) fileId: Long
    ) {
        companyService.removeCompanyIntroductionFile(user?: throw TokenNotFoundException(), fileId)
    }



    @PutMapping("/logo")
    fun changeCompanyLogo(
        @AuthenticationPrincipal user: User?,
        @RequestPart logo: MultipartFile
    ) {
        companyService.changeCompanyLogo(
            user?: throw TokenNotFoundException(),
            logo
        )
    }


    @PutMapping("/photo")
    fun addCompanyPhoto(
        @AuthenticationPrincipal user: User?,
        @RequestPart photo: MultipartFile
    ) {
        companyService.addCompanyPhoto(
            user?: throw TokenNotFoundException(),
            photo
        )
    }

    @DeleteMapping("/photo")
    fun removeCompanyPhoto(
        @AuthenticationPrincipal user: User?,
        @RequestParam(required = true) fileId: Long
    ) {
        companyService.removeCompanyPhoto(
            user?: throw TokenNotFoundException(),
            fileId
        )
    }


    @PostMapping("/associate")
    fun makeAssociated(
        @AuthenticationPrincipal user: User?,
        @RequestParam(required = true) companyId: Long
    ) {
        companyService.makeAssociated(
            user?: throw TokenNotFoundException(),
            companyId
        )
    }

    @PostMapping("/{companyId}/student/{studentId}")
    fun hireStudent(
        @AuthenticationPrincipal user: User?,
        @PathVariable studentId: Long,
        @PathVariable companyId: Long,
        @RequestParam(required = true) startDate: LocalDate,
        @RequestParam(required = true) endDate: LocalDate
    ) {
        companyService.hireStudent(
            user?: throw TokenNotFoundException(),
            studentId,
            companyId,
            startDate,
            endDate
        )
    }

    @DeleteMapping("/{companyId}/student/{studentId}")
    fun fireStudent(
        @AuthenticationPrincipal user: User?,
        @PathVariable studentId: Long,
        @PathVariable companyId: Long,
    ) {
        companyService.fireStudent(
            user?: throw TokenNotFoundException(),
            studentId,
            companyId
        )
    }


}