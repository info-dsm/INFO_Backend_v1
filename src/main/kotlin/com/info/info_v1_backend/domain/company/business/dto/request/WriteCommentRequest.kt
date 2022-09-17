package com.info.info_v1_backend.domain.company.business.dto.request

import javax.validation.constraints.Size

data class WriteCommentRequest(
    @field:Size(max = 255, min = 5, message = "댓글은 5자 이상, 255자 이하로 작성되어야합니다.")
    val content: String
)
