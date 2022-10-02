package com.info.info_v1_backend.domain.project.business.dto.request

import com.info.info_v1_backend.domain.project.data.entity.Creation
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import com.info.info_v1_backend.global.file.entity.File

data class EditRegisteredProjectDto(
    val id: Long?,
    val name: String?,
    val shortContent: String?,
    val haveSeenCount: Long?,
    val status: ProjectStatus?,
    val purpose: String?,
    val theoreticalBackground: MutableList<String>?,
    val processList: MutableList<String>?,
    val result: String?,
    val conclusion: String?,
    val referenceList: MutableList<String>?,
    val creationList: MutableList<Creation>?,
    val codeLinkList: MutableList<String>?,
    val tagList: MutableList<String>?,
    val photoList: MutableList<File>?
)
