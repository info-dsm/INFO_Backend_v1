package com.info.info_v1_backend.domain.board.data.entity

import com.info.info_v1_backend.domain.board.data.entity.type.ClassNum
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Embeddable

@Embeddable
class PerClassEmployInfo(
    classNum: ClassNum,
    indicationList: MutableList<Indication>
) {

    @Column(name = "class_num", nullable = false)
    var classNum: ClassNum = classNum

    @ElementCollection
    var indicationList: MutableList<Indication> = indicationList

}