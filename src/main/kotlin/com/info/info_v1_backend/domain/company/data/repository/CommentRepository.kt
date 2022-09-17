package com.info.info_v1_backend.domain.company.data.repository

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.company.data.entity.comment.Comment
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CommentRepository: JpaRepository<Comment, Long> {

    fun findByWriterAndCompany(student: Student, company: Company): Optional<Comment>
    fun findByWriterAndId(student: Student, id: Long): Optional<Comment>
}