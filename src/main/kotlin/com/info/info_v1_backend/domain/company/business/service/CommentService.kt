package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.company.business.dto.request.EditCommentRequest
import com.info.info_v1_backend.domain.company.business.dto.request.WriteCommentRequest
import com.info.info_v1_backend.domain.company.business.dto.response.CommentResponse
import com.info.info_v1_backend.domain.company.data.entity.company.Company

interface CommentService {

    fun writeComment(request: WriteCommentRequest, companyId: Long)
    fun editComment(request: EditCommentRequest, commentId: Long)
    fun deleteComment(id: Long)

}