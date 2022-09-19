package com.info.info_v1_backend.domain.project.data.entity.type

enum class ProjectStatus(
    meaning: String
) {
    APPROVE("수락"),
    REJECT("거절"),
    WAITING("대기"),

}