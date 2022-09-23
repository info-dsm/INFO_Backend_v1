package com.info.info_v1_backend.domain.project.business.dto.request

import com.info.info_v1_backend.domain.project.business.dto.common.StudentIdDto

data class RegisteredProjectEditRequest(
    val projectId: Long,
    val name: String?,
    val shortContent: String?,
    val purpose: String, // 프로젝트 실행동기 및 목정
    val theoreticalBackground: MutableList<String>, //이론적 배경
    val processList: MutableList<String>, //프로젝트 방법 및 과정
    val result: String,//프로젝트 결과
    val conclusion: String,//결론 및 고찰
    val referenceList: MutableList<String>,//참고 문헌 링크
    val codeLinkList: MutableList<String>,
    val studentIdList: MutableList<StudentIdDto>,
    val tagList: MutableList<String>
)
