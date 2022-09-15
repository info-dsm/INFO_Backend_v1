package com.info.info_v1_backend.domain.employment.data.entity

import com.info.info_v1_backend.domain.employment.data.type.ClassNum
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Embeddable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

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