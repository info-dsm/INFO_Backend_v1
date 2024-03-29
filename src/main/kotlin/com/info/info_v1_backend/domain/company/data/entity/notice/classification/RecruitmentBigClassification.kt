package com.info.info_v1_backend.domain.company.data.entity.notice.classification

import com.info.info_v1_backend.domain.company.business.dto.response.notice.BigClassificationResponse
import com.info.info_v1_backend.domain.company.data.entity.notice.recruitment.RecruitmentBusiness
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany


@Entity
class RecruitmentBigClassification(
    name: String
): BaseTimeEntity() {

    @Id
    @Column(name = "notice_big_classification_name", nullable = false)
    val name: String = name

    @OneToMany(mappedBy = "bigClassification")
    var  recruitmentBusinessList: MutableList<RecruitmentBusiness> = ArrayList()
        protected set

    @OneToMany
    var smallClassificationList: MutableList<RecruitmentSmallClassification>  = ArrayList()
        protected set

    fun toBigClassificationResponse(): BigClassificationResponse {
        return BigClassificationResponse(
            this.name
        )
    }

}