package com.info.info_v1_backend.domain.company.presentation

import com.info.info_v1_backend.domain.company.business.dto.request.notice.CloseNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.EditNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.RegisterNoticeRequest
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MaximumNoticeResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MinimumNoticeResponse
import com.info.info_v1_backend.domain.company.business.service.NoticeService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/info/v1/notice")
@Validated
class NoticeController(
    private val noticeService: NoticeService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registerNotice(
        @RequestBody request: RegisterNoticeRequest
    ) {
        noticeService.registerNotice(request)
    }

    @PatchMapping
    fun editNotice(
        @RequestBody request: EditNoticeRequest,
        @RequestParam(required = true) noticeId: Long
    ) {
        noticeService.editNotice(request, noticeId)
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMapping(
        @RequestParam(required = true) noticeId: Long
    ) {
        noticeService.deleteNotice(noticeId)
    }

    @DeleteMapping("/close")
    fun closeNotice(
        @RequestBody request: CloseNoticeRequest,
        @RequestParam(required = true) noticeId: Long
    ) {
        noticeService.closeNotice(request, noticeId)
    }

    @GetMapping("/list")
    fun getMinimumNoticeList(
        @RequestParam(defaultValue = "0") idx: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "true") isExpired: Boolean
    ): Page<MinimumNoticeResponse> {
        return noticeService.getMinimumNoticeList(idx, size, isExpired)
    }

    @GetMapping
    fun getMaximumNotice(
        @RequestParam(required = true) id: Long
    ): MaximumNoticeResponse {
        return noticeService.getMaximumNotice(id)
    }

    @GetMapping
    fun searchMinimumNotice(
        @RequestParam query: String
    ): List<MinimumNoticeResponse> {
        return noticeService.searchMinimumNoticeList(query)
    }

}