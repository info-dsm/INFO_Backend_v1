package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.company.business.dto.request.comment.EditCommentRequest
import com.info.info_v1_backend.domain.company.business.dto.request.comment.WriteCommentRequest

interface CommentService {

    fun writeComment(request: WriteCommentRequest, companyId: String)
    fun editComment(request: EditCommentRequest, commentId: Long)
    fun deleteComment(id: Long)

}