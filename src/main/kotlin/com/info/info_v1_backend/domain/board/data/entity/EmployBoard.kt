package com.info.info_v1_backend.domain.board.data.entity

import com.info.info_v1_backend.domain.board.business.dto.EmployBoardDto
import com.info.info_v1_backend.domain.board.business.dto.PerClassEmployInfoDto
import com.info.info_v1_backend.domain.company.data.entity.type.MajorType
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class EmployBoard(
    totalRate: Int,
    bestMajor: MajorType,
    perClassEmployInfoList: MutableList<PerClassEmployInfo>
): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "total_rate")
    val totalRate = totalRate

    @Column(name = "best_major")
    val bestMajor: MajorType = bestMajor

    @ElementCollection
    val perClassEmployInfoList: MutableList<PerClassEmployInfo> = perClassEmployInfoList

    fun toEmployBoardDto(): EmployBoardDto {
        return EmployBoardDto(
            this.totalRate,
            this.bestMajor,
            this.perClassEmployInfoList.map {
                it.toPerClassEmployInfoDto()
            } as MutableList<PerClassEmployInfoDto>
        )
    }


}