package com.info.info_v1_backend.domain.company.data.entity.type

enum class MajorType(
    val major: String,
) {
    SW_DEV("소프트웨어개발과"),
    EMBEDEED("임베디드과"),
    SECURITY("정보보안과"),
    NONE("학과무관")

}