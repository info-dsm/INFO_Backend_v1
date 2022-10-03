package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.Teacher
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.exception.IsNotStudentException
import com.info.info_v1_backend.domain.company.business.dto.request.comment.EditCommentRequest
import com.info.info_v1_backend.domain.company.business.dto.request.comment.WriteCommentRequest
import com.info.info_v1_backend.domain.company.data.entity.comment.Comment
import com.info.info_v1_backend.domain.company.data.repository.company.CommentRepository
import com.info.info_v1_backend.domain.company.data.repository.company.CompanyRepository
import com.info.info_v1_backend.domain.company.exception.CommentNotFoundException
import com.info.info_v1_backend.domain.company.exception.CompanyNotFoundException
import com.info.info_v1_backend.global.error.common.NoAuthenticationException
import com.info.info_v1_backend.global.util.user.CurrentUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(
    private val companyRepository: CompanyRepository,
    private val commentRepository: CommentRepository,
): CommentService {

    override fun writeComment(user: User, request: WriteCommentRequest, companyId: Long) {
        if (user is Student) {
            val company = companyRepository.findByIdOrNull(companyId)?: throw CompanyNotFoundException(companyId.toString())
            if (company.hiredStudentList.map { it.student }.contains(user) ||
                company.fieldTrainingList.map { it.student }.contains(user)) {
                commentRepository.save(
                    Comment(
                        request.content,
                        company,
                        user
                    )
                )
            }
        }
        throw IsNotStudentException(user.roleList.toString())
    }

    override fun editComment(user: User, request: EditCommentRequest, commentId: Long) {
        if (user is Student) {
            val comment = commentRepository.findByWriterAndId(user, commentId).orElse(null)?:
            throw CommentNotFoundException(commentId.toString())

            comment.editComment(request)
        }
        throw IsNotStudentException(user.roleList.toString())
    }

    override fun deleteComment(user: User, id: Long) {
        if (user is Student) {
            val comment = commentRepository.findByWriterAndId(user, id).orElse(null)
                ?: throw CommentNotFoundException(id.toString())
            comment.makeDelete()
        } else if (user is Teacher) {
            val comment = commentRepository.findById(id).orElse(null) ?: throw CommentNotFoundException(id.toString())
            comment.makeDelete()
        } else throw NoAuthenticationException(user.roleList.toString())
    }

}