package com.info.info_v1_backend.domain.company.business.dto.request.notice.edit

data class EditWorkPlaceRequest(
    val isSameWithCompanyAddress: Boolean?,
    val otherPlace: String?
)
