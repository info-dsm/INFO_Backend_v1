package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.comment.EditCommentRequest
import com.info.info_v1_backend.domain.company.business.dto.request.comment.WriteCommentRequest

interface CommentService {

    fun writeComment(user: User, request: WriteCommentRequest, companyId: Long)
    fun editComment(user: User, request: EditCommentRequest, commentId: Long)
    fun deleteComment(user: User, id: Long)

}