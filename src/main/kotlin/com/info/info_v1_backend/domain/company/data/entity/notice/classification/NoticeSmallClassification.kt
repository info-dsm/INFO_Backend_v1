package com.info.info_v1_backend.domain.company.data.entity.notice.classification

import com.info.info_v1_backend.domain.company.data.entity.notice.recruitment.RecruitmentBusiness
import javax.persistence.*


@Entity
class NoticeSmallClassification(
    name: String
) {

    @Id
    @Column(name = "notice_small_classification_name")
    val name: String = name

    @OneToMany(mappedBy = "smallClassification")
    var  recruitmentBusinessList: MutableList<RecruitmentBusiness> = ArrayList()
        protected set
}