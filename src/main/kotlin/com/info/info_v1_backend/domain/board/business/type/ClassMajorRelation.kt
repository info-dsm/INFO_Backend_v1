package com.info.info_v1_backend.domain.board.business.type

enum class ClassMajorRelation(
    val major: String,
    val classKey: Int,
    val secondClassKey: Int?
) {
    SW("소개과", 1, 2),
    EMBE("임베과", 3, null),
    SECU("보안과", 4, null)

}