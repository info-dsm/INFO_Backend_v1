package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.exception.IsNotStudentException
import com.info.info_v1_backend.domain.company.business.dto.request.EditCommentRequest
import com.info.info_v1_backend.domain.company.business.dto.request.WriteCommentRequest
import com.info.info_v1_backend.domain.company.business.dto.response.CommentResponse
import com.info.info_v1_backend.domain.company.data.entity.comment.Comment
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.repository.CommentRepository
import com.info.info_v1_backend.domain.company.data.repository.CompanyRepository
import com.info.info_v1_backend.domain.company.exception.CommentNotFoundException
import com.info.info_v1_backend.domain.company.exception.CompanyNotFoundException
import com.info.info_v1_backend.domain.company.exception.StudentAlreadyWrittenComment
import com.info.info_v1_backend.global.util.user.CurrentUtil
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(
    private val companyRepository: CompanyRepository,
    private val commentRepository: CommentRepository,
    private val currentUtil: CurrentUtil
): CommentService {

    override fun writeComment(request: WriteCommentRequest, companyId: Long) {
        val current = currentUtil.getCurrentUser()
        if (current is Student) {
            val company = companyRepository.findByIdAndStudentListContains(companyId, current).orElse(null)
                ?: throw CompanyNotFoundException(companyId.toString())
            commentRepository.findByWriterAndCompany(current, company).orElse(null)?. let{
                throw StudentAlreadyWrittenComment(it.writer.email)
            }?: let {
                commentRepository.save(
                    Comment(
                        request.content,
                        company,
                        current
                    )
                )
            }
        }
        throw IsNotStudentException(current.roleList.toString())
    }

    override fun editComment(request: EditCommentRequest, id: Long) {
        val current = currentUtil.getCurrentUser()
        if (current is Student) {
            val comment = commentRepository.findByWriterAndId(current, id).orElse(null)?:
            throw CommentNotFoundException(id.toString())

            comment.editComment(request)
        }
        throw IsNotStudentException(current.roleList.toString())
    }

    override fun deleteComment(id: Long) {
        val current = currentUtil.getCurrentUser()

        if (current is Student) {
            val comment = commentRepository.findByWriterAndId(current, id).orElse(null)
                ?: throw CommentNotFoundException(id.toString())
            comment.makeDelete()
        }
        val comment = commentRepository.findById(id).orElse(null)?: throw CommentNotFoundException(id.toString())
        comment.makeDelete()
    }

}