package com.info.info_v1_backend.domain.company.presentation

import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.notice.edit.EditNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.RegisterNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MaximumNoticeWithoutPayResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MinimumNoticeResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.NoticeWithIsApproveResponse
import com.info.info_v1_backend.domain.company.business.service.NoticeService
import com.info.info_v1_backend.domain.company.data.entity.notice.interview.InterviewProcess
import com.info.info_v1_backend.global.error.common.TokenCanNotBeNullException
import com.info.info_v1_backend.global.file.dto.FileResponse
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/info/v1/notice")
@Validated
class NoticeController(
    private val noticeService: NoticeService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registerNotice(
        @AuthenticationPrincipal user: User?,
        @RequestPart request: RegisterNoticeRequest,
        @RequestPart attachment: List<MultipartFile>
    ) {
        noticeService.registerNotice(user?: throw TokenCanNotBeNullException(), request, attachment)
    }


    @PutMapping("/attachment")
    fun changeAttachment(
        @AuthenticationPrincipal user: User?,
        @RequestPart attachment: List<MultipartFile>,
        @RequestParam(required = true) noticeId: Long
    ) {
        noticeService.changeAttachment(user?: throw TokenCanNotBeNullException(), attachment, noticeId)
    }

    @PutMapping("/interview/process")
    fun changeInterviewProcess(
        @AuthenticationPrincipal user: User?,
        @RequestBody interviewProcessMap: Map<Int, InterviewProcess>,
        @RequestParam(required = true) noticeId: Long
    ) {
        noticeService.changeInterviewProcess(
            user?: throw TokenCanNotBeNullException(),
            interviewProcessMap,
            noticeId
        )
    }


    //add attachment

    @PatchMapping
    fun editNotice(
        @AuthenticationPrincipal user: User?,
        @RequestBody request: EditNoticeRequest,
        @RequestParam(required = true) noticeId: Long
    ) {
        noticeService.editNotice(user?: throw TokenCanNotBeNullException(), request, noticeId)
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMapping(
        @AuthenticationPrincipal user: User?,
        @RequestParam(required = true) noticeId: Long
    ) {
        noticeService.deleteNotice(user?: throw TokenCanNotBeNullException(), noticeId)
    }

    @GetMapping("/me")
    fun getMyNoticeList(
        @AuthenticationPrincipal user: User?
    ): List<NoticeWithIsApproveResponse> {
        return noticeService.getMyNoticeList(
            user?: throw TokenCanNotBeNullException()
        )
    }


    @PutMapping("/approve")
    fun approveNotice(
        @AuthenticationPrincipal user: User?,
        @RequestParam(required = true) noticeId: Long
    ) {
        return noticeService.approveNotice(user?: throw TokenCanNotBeNullException(), noticeId)
    }

    @DeleteMapping("/approve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun rejectNotice(
        @AuthenticationPrincipal user: User?,
        @RequestParam(required = true) noticeId: Long
    ) {
        return noticeService.rejectNotice(user?: throw TokenCanNotBeNullException(), noticeId)
    }


    @GetMapping("/waiting-notice/list")
    fun getWaitingNoticeList(
        @AuthenticationPrincipal user: User?,
        @RequestParam(defaultValue = "0") idx: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<MinimumNoticeResponse> {
        return noticeService.getWaitingNoticeList(
            user?: throw TokenCanNotBeNullException(),
            idx,
            size
        )
    }


    @GetMapping("/list")
    fun getMinimumNoticeList(
        @RequestParam(defaultValue = "0") idx: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): Page<MinimumNoticeResponse> {
        return noticeService.getMinimumNoticeList(idx, size)
    }

    @GetMapping
    fun getMaximumNotice(
        @RequestParam(required = true) id: Long
    ): MaximumNoticeWithoutPayResponse {
        return noticeService.getMaximumNotice(id)
    }

    @GetMapping("/search")
    fun searchMinimumNotice(
        @RequestParam query: String
    ): Page<MinimumNoticeResponse> {
        return noticeService.searchMinimumNoticeList(query)
    }

    @PostMapping("/out")
    fun printNotice(
        @AuthenticationPrincipal user: User?,
        @RequestParam(required = true) noticeId: Long
    ): FileResponse {
        return noticeService.printNotice(user?: throw TokenCanNotBeNullException(), noticeId)
    }


}