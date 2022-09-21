package com.info.info_v1_backend.domain.company.data.entity.notice.embeddable

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

}