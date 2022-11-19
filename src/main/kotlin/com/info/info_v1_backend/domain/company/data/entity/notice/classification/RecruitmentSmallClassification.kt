package com.info.info_v1_backend.domain.company.data.entity.notice.classification

import com.info.info_v1_backend.domain.company.business.dto.response.notice.BigClassificationResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.ClassificationResponse
import com.info.info_v1_backend.domain.company.data.entity.notice.recruitment.RecruitmentBusiness
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import javax.persistence.*


@Entity
class RecruitmentSmallClassification(
    name: String,
    bigClassification: RecruitmentBigClassification
): BaseTimeEntity() {

    @Id
    @Column(name = "notice_small_classification_name")
    val name: String = name

    @OneToMany(mappedBy = "smallClassification")
    var recruitmentBusinessList: MutableList<RecruitmentBusiness> = ArrayList()
        protected set

    @ManyToOne
    @JoinColumn(name = "big_classifiction", nullable = false)
    val bigClassification: RecruitmentBigClassification = bigClassification

    fun toClassificationResponse(): ClassificationResponse {
        return ClassificationResponse(
            BigClassificationResponse(
                this.bigClassification.name
            ),
            this.name
        )
    }
}