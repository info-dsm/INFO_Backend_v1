package com.info.info_v1_backend.domain.company.business.dto.request.notice.register

import com.info.info_v1_backend.domain.company.data.entity.notice.embeddable.WorkPlace

data class WorkPlaceRequest (
    val isSameWithCompanyAddress: Boolean,
    val otherPlace: String?
) {
    fun toWorkPlace(): WorkPlace {
        return WorkPlace(
            this.isSameWithCompanyAddress,
            this.otherPlace
        )
    }
}