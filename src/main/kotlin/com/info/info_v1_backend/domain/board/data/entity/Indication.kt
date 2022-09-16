package com.info.info_v1_backend.domain.board.data.entity

import com.info.info_v1_backend.global.image.entity.File
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Embeddable
class Indication(
    companyId: Long,
    companyShortName: String,
    image: File,
    perClassEmployInfoList: MutableList<PerClassEmployInfo>
) {
    @Column(name = "company_id", nullable = false)
    val companyId: Long = companyId

    @Column(name = "company_short_name", nullable = false)
    val companyShortName: String = companyShortName

    @ManyToOne
    @JoinColumn(name = "indication_image", nullable = false)
    val image: File = image

    @ElementCollection
    val perClassEmployInfo: MutableList<PerClassEmployInfo> = perClassEmployInfoList


}