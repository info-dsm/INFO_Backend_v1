package com.info.info_v1_backend.domain.project.business.dto.request

import com.info.info_v1_backend.domain.project.data.entity.Creation
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import com.info.info_v1_backend.global.file.entity.File

data class EditIndividualProjectDto(
    val id: Long?,
    val name: String?,
    val shortContent: String?,
    val haveSeenCount: Long?,
    val creationList: List<Creation>?,
    val codeLinkList: MutableList<String>?,
    val tagList: MutableList<String>?,
    val status: ProjectStatus?,
    val photoList: MutableList<File>?
)
