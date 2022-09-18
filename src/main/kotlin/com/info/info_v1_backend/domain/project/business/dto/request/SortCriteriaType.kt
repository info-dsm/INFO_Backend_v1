package com.info.info_v1_backend.domain.project.business.dto.request

enum class SortCriteriaType(
    val criteria: String
) {
    DATE("createdAt"),
    SEEN("haveSeenCount")

}