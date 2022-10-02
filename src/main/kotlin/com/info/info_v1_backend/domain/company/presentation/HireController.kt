package com.info.info_v1_backend.domain.company.presentation

import com.info.info_v1_backend.domain.auth.business.dto.response.MinimumStudent
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.notice.CloseNoticeRequest
import com.info.info_v1_backend.domain.company.business.service.HireService
import com.info.info_v1_backend.global.error.common.TokenNotFoundException
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

@RestController
@RequestMapping("/api/info/v1/hire")
@Validated
class HireController(
    private val hireService: HireService
) {

    @PostMapping("/apply/{noticeId}")
    fun applyNotice(
        @AuthenticationPrincipal user: User?,
        @PathVariable noticeId: Long,
        @RequestPart(required = true) reporter: List<MultipartFile>
    ){
        return hireService.applyNotice(
            user?: throw TokenNotFoundException(),
            noticeId,
            reporter
        )
    }

    @GetMapping("/apply/{noticeId}")
    fun getApplierList(
        @AuthenticationPrincipal user: User?,
        @PathVariable noticeId: Long,
        @RequestParam(defaultValue = "0") idx: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<MinimumStudent> {
        return hireService.getApplierList(
            user?: throw TokenNotFoundException(),
            noticeId,
            idx,
            size
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
            user?: throw TokenNotFoundException(),
            noticeId,
            studentId
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
            user?: throw TokenNotFoundException(),
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
            user?: throw TokenNotFoundException(),
            studentId,
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
            user?: throw TokenNotFoundException(),
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
            user?: throw TokenNotFoundException(),
            studentId,
            companyId
        )
    }

}