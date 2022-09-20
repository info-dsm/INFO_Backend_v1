package com.info.info_v1_backend.domain.company.data.entity.notice.recruitment

import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.notice.classification.NoticeBigClassification
import com.info.info_v1_backend.domain.company.data.entity.notice.classification.NoticeSmallClassification
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import org.springframework.data.domain.Persistable
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "recruitment")
@IdClass(RecruitmentBusinessIdClass::class)
class RecruitmentBusiness(
    bigClassification: NoticeBigClassification,
    smallClassification: NoticeSmallClassification,
    numberOfEmplyee: Int,
    notice: Notice,
    detailBusinessDescription: String?
): BaseTimeEntity(), Serializable, Persistable<String> {

    @Id
    @ManyToOne
    @JoinColumn(
        name = "notice_big_classification",
        nullable = false
    )
    var bigClassification: NoticeBigClassification = bigClassification

    @Id
    @ManyToOne
    @JoinColumn(
        name = "notice_small_classification",
        nullable = false
    )
    var smallClassification: NoticeSmallClassification = smallClassification

    @ManyToOne
    @JoinColumn(name = "notice", nullable = false)
    val notice: Notice = notice

    @Column(
        name = "detail_business_description",
        nullable = false
    )
    var detailBusinessDescription: String? = detailBusinessDescription
        protected set


    @Column(name = "number_of_emplyee", nullable = false)
    var numberOfEmplyee: Int = numberOfEmplyee
        protected set

    override fun getId(): String? {
        return this.id
    }

    override fun isNew(): Boolean {
        return this.createdAt == null
    }


}