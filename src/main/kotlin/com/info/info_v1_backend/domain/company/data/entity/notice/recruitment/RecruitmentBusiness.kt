package com.info.info_v1_backend.domain.company.data.entity.notice.recruitment

import com.info.info_v1_backend.domain.company.business.dto.request.notice.edit.EditRecruitmentRequest
import com.info.info_v1_backend.domain.company.business.dto.response.notice.RecruitmentBusinessResponse
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.notice.certificate.Certificate
import com.info.info_v1_backend.domain.company.data.entity.notice.classification.RecruitmentBigClassification
import com.info.info_v1_backend.domain.company.data.entity.notice.classification.RecruitmentSmallClassification
import com.info.info_v1_backend.domain.company.data.entity.notice.language.LanguageUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.technology.TechnologyUsage
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import javax.persistence.*

@Entity
@Table(name = "recruitment")
class RecruitmentBusiness(
    bigClassification: RecruitmentBigClassification,
    smallClassification: RecruitmentSmallClassification,
    numberOfEmplyee: Int,
    notice: Notice,
    detailBusinessDescription: String?,
    gradeCutLine: Int?,
//    languageUsageSet: Set<LanguageUsage>,
//    technologyList: MutableList<TechnologyUsage>,
): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne
    @JoinColumn(
        name = "notice_big_classification",
        nullable = false
    )
    var bigClassification: RecruitmentBigClassification = bigClassification
        protected set

    @ManyToOne
    @JoinColumn(
        name = "notice_small_classification",
        nullable = false
    )
    var smallClassification: RecruitmentSmallClassification = smallClassification
        protected set

    @OneToOne
    @JoinColumn(name = "notice_id", nullable = false)
    val notice: Notice = notice

    @Column(
        name = "detail_business_description",
        nullable = false
    )
    var detailBusinessDescription: String? = detailBusinessDescription
        protected set

    @OneToMany(mappedBy = "recruitmentBusiness")
    var languageUsageSet: MutableList<LanguageUsage> = ArrayList()
        protected set

    @OneToMany(mappedBy = "recruitmentBusiness")
    var technologyList: MutableList<TechnologyUsage> = ArrayList()
        protected set

    @OneToMany
    var needCertificateList: MutableList<Certificate> = ArrayList()
        protected set

    @Column(name = "number_of_emplyee", nullable = false)
    var numberOfEmplyee: Int = numberOfEmplyee
        protected set

    @Column(name = "recruitment_business_grade_cut_line", nullable = true)
    var gradeCutLine: Int? = gradeCutLine
        protected set

    fun toRecruitmentBusinessResponse(): RecruitmentBusinessResponse {
        return RecruitmentBusinessResponse(
            this.bigClassification.toBigClassificationResponse(),
            this.smallClassification.toSmallClassification(),
            this.numberOfEmplyee,
            this.detailBusinessDescription,
            this.needCertificateList.map {
                 it.name
            },
            this.gradeCutLine
        )
    }

    fun editRecruitmentBusiness(r: EditRecruitmentRequest) {
        r.numberOfEmployee?.let {
            this.numberOfEmplyee = r.numberOfEmployee
        }
        r.detailBusinessDescription?.let {
            this.detailBusinessDescription = r.detailBusinessDescription
        }
        r.gradeCutLine?.let {
            this.gradeCutLine = r.gradeCutLine
        }
    }

}