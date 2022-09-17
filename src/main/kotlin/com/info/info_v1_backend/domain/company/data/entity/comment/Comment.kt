package com.info.info_v1_backend.domain.company.data.entity.comment

import com.info.info_v1_backend.domain.company.business.dto.response.CommentResponse
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
class Comment(

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null


    fun toCommentResponse(): CommentResponse {
        return CommentResponse()
    }


}