package com.info.info_v1_backend.domain.company.presentation

import com.info.info_v1_backend.domain.company.business.dto.request.comment.EditCommentRequest
import com.info.info_v1_backend.domain.company.business.dto.request.comment.WriteCommentRequest
import com.info.info_v1_backend.domain.company.business.service.CommentService
import org.springframework.http.HttpStatus
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
        @RequestBody request: WriteCommentRequest,
        @RequestParam companyId: Long
    ) {
        commentService.writeComment(request, companyId)
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    fun editComment(
        @RequestBody request: EditCommentRequest,
        @RequestParam commentId: Long
    ) {
        commentService.editComment(request, commentId)
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun deleteComment(@RequestParam id: Long) {
        commentService.deleteComment(id)
    }

}