package com.info.info_v1_backend.domain.employment.data.entity

import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class EmployBoard(
    totalRate: Int,
    bestMajor: Int,
    perClassEmployInfoList: MutableList<PerClassEmployInfo>
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "total_rate")
    val totalRate = totalRate

    @Column(name = "best_major")
    val bestMajor = bestMajor

    @ElementCollection
    val perClassEmployInfoList: MutableList<PerClassEmployInfo> = perClassEmployInfoList


}