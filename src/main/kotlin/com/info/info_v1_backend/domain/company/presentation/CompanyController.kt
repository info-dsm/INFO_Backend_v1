package com.info.info_v1_backend.domain.company.presentation

import com.info.info_v1_backend.domain.auth.business.dto.request.CompanySignupRequest
import com.info.info_v1_backend.domain.auth.business.service.AuthService
import com.info.info_v1_backend.domain.auth.business.service.EmailService
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.exception.CheckEmailCodeException
import com.info.info_v1_backend.domain.company.business.dto.request.company.EditCompanyRequest
import com.info.info_v1_backend.domain.company.business.dto.response.company.BusinessAreaResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyWithIsWorkingResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse
import com.info.info_v1_backend.domain.company.business.service.CompanyService
import com.info.info_v1_backend.domain.company.data.entity.company.tag.BusinessArea
import com.info.info_v1_backend.global.error.common.TokenCanNotBeNullException
import com.info.info_v1_backend.global.file.dto.FileResponse
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
import java.time.Year
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/api/info/v1/company")
@Validated
class CompanyController(
    private val companyService: CompanyService,
    private val emailService: EmailService,
    private val authService: AuthService
) {

    @PostMapping("/email")
    fun sendCompanyEmail(
        @Valid
        @NotNull
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        @RequestParam
        email: String
    ){
        emailService.sendCodeToEmail(email)
    }

    @PostMapping
    fun registerCompany(
        @Valid
        @RequestPart request: CompanySignupRequest,
        @RequestParam emailCheckCode: String,
        @RequestPart businessRegisteredCertificate: MultipartFile,
        @RequestPart companyIntroductionFile: List<MultipartFile>,
        @RequestPart companyLogo: MultipartFile,
        @RequestPart companyPhotoList: List<MultipartFile>
    ) {
        if (authService.checkEmail(request.companyContact.email, emailCheckCode)) {
            companyService.registerCompany(
                request,
                emailCheckCode,
                businessRegisteredCertificate,
                companyIntroductionFile,
                companyLogo,
                companyPhotoList
            )
        } else throw CheckEmailCodeException(emailCheckCode)
    }


    @PatchMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    fun editCompany(
        @AuthenticationPrincipal user: User?,
        @RequestBody request: EditCompanyRequest,
        @PathVariable companyId: Long
    ) {
        companyService.editCompany(user
            ?: throw TokenCanNotBeNullException(), request, companyId)
    }

    @GetMapping("/business-area")
    fun getBusinessAreaList(): List<BusinessAreaResponse> {
        return companyService.getBusinessAreaList()
    }

    @PutMapping("/business-area/{businessAreaId}")
    fun addBusinessArea(
        @AuthenticationPrincipal user: User?,
        @PathVariable businessAreaId: String
    ) {
        companyService.addBusinessArea(
            user?: throw TokenCanNotBeNullException(),
            businessAreaId
        )
    }

    @DeleteMapping("/business-area/{businessAreaId}")
    fun removeBusinessArea(
        @AuthenticationPrincipal user: User?,
        @PathVariable businessAreaId: String
    ) {
        companyService.removeBusinessArea(
            user?: throw TokenCanNotBeNullException(),
            businessAreaId
        )
    }



    @PutMapping("/certificate")
    fun changeBusinessRegisteredCertificate(
        @AuthenticationPrincipal user: User?, 
        @RequestPart certificate: MultipartFile
    ) {
        companyService.changeBusinessRegisteredCertificate(user
            ?: throw TokenCanNotBeNullException(), certificate)
    }

    @PutMapping("/introduction")
    fun addCompanyIntroductionFile(
        @AuthenticationPrincipal user: User?, 
        @RequestPart introduction: MultipartFile
    ) {
        companyService.addCompanyIntroductionFile(user
            ?: throw TokenCanNotBeNullException(), introduction)
    }

    @DeleteMapping("/introduction")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removeCompanyIntroductionFile(
        @AuthenticationPrincipal user: User?,
        @RequestParam(required = true) fileId: Long
    ) {
        companyService.removeCompanyIntroductionFile(user
            ?: throw TokenCanNotBeNullException(), fileId)
    }

    @PutMapping("/logo")
    fun changeCompanyLogo(
        @AuthenticationPrincipal user: User?,
        @RequestPart logo: MultipartFile
    ) {
        companyService.changeCompanyLogo(
            user?: throw TokenCanNotBeNullException(),
            logo
        )
    }

    @PutMapping("/photo")
    fun addCompanyPhoto(
        @AuthenticationPrincipal user: User?,
        @RequestPart photo: MultipartFile
    ) {
        companyService.addCompanyPhoto(
            user?: throw TokenCanNotBeNullException(),
            photo
        )
    }

    @DeleteMapping("/photo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removeCompanyPhoto(
        @AuthenticationPrincipal user: User?,
        @RequestParam(required = true) fileId: Long
    ) {
        companyService.removeCompanyPhoto(
            user?: throw TokenCanNotBeNullException(),
            fileId
        )
    }



    @PostMapping("/associate")
    fun makeAssociated(
        @AuthenticationPrincipal user: User?,
        @RequestParam(required = true) companyId: Long
    ) {
        companyService.makeAssociated(
            user?: throw TokenCanNotBeNullException(),
            companyId
        )
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
    ): List<MaximumCompanyWithIsWorkingResponse> {
        return companyService.getEntireMaximumCompanyByUserId(user
            ?: throw TokenCanNotBeNullException(), id)
    }

    @GetMapping("/search")
    fun searchCompany(
        @RequestParam(required = true) query: String
    ): Page<MinimumCompanyResponse>? {
        return companyService.searchCompany(query)
    }

    @GetMapping("/{companyId}/business-registered-certificate")
    fun getBusinessRegisteredCertificate(
        @AuthenticationPrincipal user: User?,
        @PathVariable(required = true) companyId: Long
    ): FileResponse {
        return companyService.getBusinessRegisteredCertificate(user?: throw TokenCanNotBeNullException(),
            companyId
        ).toFileResponse()
    }

    @GetMapping("/list/year")
    fun getNoticeRegisteredCompanyListByYear(
        @AuthenticationPrincipal user: User?,
        @RequestParam year: Year,
        @RequestParam idx: Int,
        @RequestParam size: Int
    ): Page<MinimumCompanyResponse> {
        return companyService.getNoticeRegisteredCompanyListByYear(
            user?: throw TokenCanNotBeNullException(),
            year,
            idx,
            size
        )
    }





}