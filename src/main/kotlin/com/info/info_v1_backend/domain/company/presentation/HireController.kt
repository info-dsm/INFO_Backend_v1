package com.info.info_v1_backend.domain.company.presentation

import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.notice.CloseNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.response.company.FieldTrainingResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.FieldTrainingStudentWithHiredResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.HiredStudentResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.ApplicantResponse
import com.info.info_v1_backend.domain.company.business.service.HireService
import com.info.info_v1_backend.global.error.common.TokenCanNotBeNullException
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.time.Year

@RestController
@RequestMapping("/api/info/v1/hire")
@Validated
class HireController(
    private val hireService: HireService
) {


    @GetMapping("/apply/{noticeId}")
    fun getApplierList(
        @AuthenticationPrincipal user: User?,
        @PathVariable noticeId: Long,
        @RequestParam(defaultValue = "0") idx: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): List<ApplicantResponse> {
        return hireService.getApplierList(
            user?: throw TokenCanNotBeNullException(),
            noticeId,
            idx,
            size
        )
    }

    @PostMapping("/apply/{noticeId}")
    fun applyNotice(
        @AuthenticationPrincipal user: User?,
        @PathVariable noticeId: Long,
        @RequestPart(required = true) reporter: List<MultipartFile>
    ){
        return hireService.applyNotice(
            user?: throw TokenCanNotBeNullException(),
            noticeId,
            reporter
        )
    }


    @DeleteMapping("/apply/{noticeId}/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun cancelApply(
        @AuthenticationPrincipal user: User?,
        @PathVariable noticeId: Long,
        @PathVariable studentId: Long
    ) {
        return hireService.cancelApply(
            user?: throw TokenCanNotBeNullException(),
            noticeId,
            studentId
        )
    }


    @GetMapping("/field-training")
    fun getFieldTrainingStudentList(
        @AuthenticationPrincipal user: User?,
        @RequestParam(required = true) companyId: Long
    ): List<FieldTrainingResponse> {
        return hireService.getFieldTrainingStudentList(
            user?: throw TokenCanNotBeNullException(),
            companyId
        )
    }


    @PostMapping("/field-training/{noticeId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun makeFieldTrainingStudent(
        @AuthenticationPrincipal user: User?,
        @PathVariable noticeId: Long,
        @RequestBody request: CloseNoticeRequest
    ) {
        hireService.makeFieldTrainingAndCloseNotice(
            user?: throw TokenCanNotBeNullException(),
            request,
            noticeId
        )
    }

    @DeleteMapping("/field-training/{companyId}/student/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun fireFieldTrainingStudent(
        @AuthenticationPrincipal user: User?,
        @PathVariable companyId: Long,
        @PathVariable studentId: Long,
    ) {
        hireService.fireFieldTrainingStudent(
            user?: throw TokenCanNotBeNullException(),
            studentId,
            companyId
        )
    }



    @GetMapping("/{companyId}/student")
    fun getHiredStudentList(
        @AuthenticationPrincipal user: User?,
        @PathVariable(required = true) companyId: Long
    ): List<HiredStudentResponse> {
        return hireService.getHiredStudentList(
            user?: throw TokenCanNotBeNullException(),
            companyId
        )
    }


    @PostMapping("/{companyId}/student/{studentId}")
    fun hireStudent(
        @AuthenticationPrincipal user: User?,
        @PathVariable studentId: Long,
        @PathVariable companyId: Long,
        @RequestParam(required = true) startDate: LocalDate,
    ) {
        hireService.hireStudent(
            user?: throw TokenCanNotBeNullException(),
            studentId,
            companyId,
            startDate,
        )
    }

    @DeleteMapping("/{companyId}/student/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun fireStudent(
        @AuthenticationPrincipal user: User?,
        @PathVariable studentId: Long,
        @PathVariable companyId: Long,
    ) {
        hireService.fireStudent(
            user?: throw TokenCanNotBeNullException(),
            studentId,
            companyId
        )
    }

    @GetMapping("/all")
    fun getFieldTrainingStudentWithHiredResponse(
        @AuthenticationPrincipal user: User?,
        @RequestParam year: Year?,
        @RequestParam(defaultValue = "0") idx: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<FieldTrainingStudentWithHiredResponse> {
        return hireService.getFieldTrainingStudentWithHiredListInThisYear(
            user?: throw TokenCanNotBeNullException(),
            idx,
            size,
            year?:Year.now()
        )
    }

}