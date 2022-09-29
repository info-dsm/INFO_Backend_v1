package com.info.info_v1_backend.domain.company.presentation

import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.comment.EditCommentRequest
import com.info.info_v1_backend.domain.company.business.dto.request.comment.WriteCommentRequest
import com.info.info_v1_backend.domain.company.business.service.CommentService
import com.info.info_v1_backend.global.error.common.TokenNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/info/v1/company/comment")
@RestController
@Validated
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun writeComment(
        @AuthenticationPrincipal user: User?,
        @RequestBody request: WriteCommentRequest,
        @RequestParam(required = true) companyId: Long
    ) {
        commentService.writeComment(user?: throw TokenNotFoundException(), request, companyId)
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    fun editComment(
        @AuthenticationPrincipal user: User?,
        @RequestBody request: EditCommentRequest,
        @RequestParam(required = true) commentId: Long
    ) {
        commentService.editComment(user?: throw TokenNotFoundException(), request, commentId)
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteComment(
        @AuthenticationPrincipal user: User?,
        @RequestParam(required = true) commentId: Long
    ) {
        commentService.deleteComment(user?: throw TokenNotFoundException(), commentId)
    }

}