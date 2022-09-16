package com.info.info_v1_backend.domain.board.data.entity

import com.info.info_v1_backend.domain.board.business.dto.IndicationDto
import com.info.info_v1_backend.global.image.entity.File
import com.info.info_v1_backend.infra.amazon.s3.dto.ImageDto
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
) {
    @Column(name = "company_id", nullable = false)
    val companyId: Long = companyId

    @Column(name = "company_short_name", nullable = false)
    val companyShortName: String = companyShortName

    @ManyToOne
    @JoinColumn(name = "indication_image", nullable = false)
    val image: File = image

    fun toIndicationDto(): IndicationDto {
        return IndicationDto(
            this.companyId,
            this.companyShortName,
            ImageDto(
                this.image.fileKey,
                this.image.id!!
            )
        )
    }

}