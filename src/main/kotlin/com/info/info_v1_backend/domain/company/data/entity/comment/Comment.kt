package com.info.info_v1_backend.domain.company.data.entity.comment

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.company.business.dto.request.EditCommentRequest
import com.info.info_v1_backend.domain.company.business.dto.response.CommentResponse
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.global.base.entity.BaseAuthorEntity
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


@Entity
class Comment(
    content: String,
    company: Company,
    student: Student
): BaseAuthorEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne
    @JoinColumn(name = "company_id")
    val company: Company = company

    @Column(name = "comment_content", length = 255)
    var content: String = content
        protected set

    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "comment_writer")
    var writer: Student = student
        protected set

    @Column(name = "comment_is_deleted", nullable = true)
    var isDeleted: Boolean = false
        protected set

    fun makeDelete() {
        this.isDeleted = true
    }

    fun toCommentResponse(): CommentResponse {
        return CommentResponse(
            this.content
        )
    }

    fun editComment(r: EditCommentRequest) {
        this.content = r.content
    }


}