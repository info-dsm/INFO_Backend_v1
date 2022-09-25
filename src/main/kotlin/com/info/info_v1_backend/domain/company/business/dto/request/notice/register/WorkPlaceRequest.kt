package com.info.info_v1_backend.domain.company.business.dto.request.notice.register

data class WorkPlaceRequest (
    val isSameWithCompanyAddress: Boolean,
    val otherPlace: String?
)
