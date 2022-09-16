package com.info.info_v1_backend.domain.board.data.entity

import com.info.info_v1_backend.domain.board.business.dto.IndicationDto
import com.info.info_v1_backend.domain.board.business.dto.PerClassEmployInfoDto
import com.info.info_v1_backend.domain.board.data.entity.type.ClassNum
import com.info.info_v1_backend.domain.company.data.entity.type.MajorType
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Embeddable

@Embeddable
class PerClassEmployInfo(
    classNum: ClassNum,
    indicationList: MutableList<Indication>,
    majorType: MajorType
) {

    @Column(name = "class_num", nullable = false)
    var classNum: ClassNum = classNum

    @Column(name = "major_type", nullable = false)
    var majorType: MajorType = majorType

    @ElementCollection
    var indicationList: MutableList<Indication> = indicationList

    fun toPerClassEmployInfoDto(): PerClassEmployInfoDto {
        return PerClassEmployInfoDto(
            this.classNum,
            this.majorType,
            this.indicationList.map {
                it.toIndicationDto()
            } as MutableList<IndicationDto>
        )
    }
}