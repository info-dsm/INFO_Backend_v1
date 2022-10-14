package com.info.info_v1_backend.domain.company.data.entity.notice.embeddable

import com.info.info_v1_backend.domain.company.business.dto.request.notice.edit.EditWorkPlaceRequest
import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.WorkPlaceRequest
import com.info.info_v1_backend.domain.company.exception.NotEmptyPlaceWIthNotSameWorkPlace
import javax.persistence.Column
import javax.persistence.Embeddable


@Embeddable
class WorkPlace(
    isSameWithCompanyAddress: Boolean,
    otherPlace: String?
) {

    @Column(name = "isSameWithCompanyAddress", nullable = false)
    var isSameWithCompanyAddress: Boolean = isSameWithCompanyAddress
        protected set

    @Column(name = "other_place", nullable = true)
    var otherPlace: String? = otherPlace
        protected set

    init {
        if (!this.isSameWithCompanyAddress && otherPlace.isNullOrEmpty()) throw NotEmptyPlaceWIthNotSameWorkPlace()
    }

    fun toWorkPlaceRequest(): WorkPlaceRequest {
        return WorkPlaceRequest(
            this.isSameWithCompanyAddress,
            this.otherPlace
        )
    }

    fun editWorkPlace(r: EditWorkPlaceRequest) {
        r.isSameWithCompanyAddress?.let {
            this.isSameWithCompanyAddress = r.isSameWithCompanyAddress
        }
        this.otherPlace = r.otherPlace
    }

}