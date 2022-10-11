package com.info.info_v1_backend.domain.company.data.entity.notice.recruitment

import com.info.info_v1_backend.domain.company.business.dto.request.notice.edit.EditRecruitmentRequest
import com.info.info_v1_backend.domain.company.business.dto.response.notice.LanguageResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.RecruitmentBusinessResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.TechnologyResponse
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.notice.certificate.Certificate
import com.info.info_v1_backend.domain.company.data.entity.notice.certificate.CertificateUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.classification.RecruitmentBigClassification
import com.info.info_v1_backend.domain.company.data.entity.notice.classification.RecruitmentSmallClassification
import com.info.info_v1_backend.domain.company.data.entity.notice.language.LanguageUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.technology.TechnologyUsage
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import org.apache.commons.lang3.mutable.Mutable
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

    @OneToMany(mappedBy = "recruitmentBusiness", orphanRemoval = true)
    var languageUsageList: MutableSet<LanguageUsage> = HashSet()
        protected set

    @OneToMany(mappedBy = "recruitmentBusiness", orphanRemoval = true)
    var technologyList: MutableSet<TechnologyUsage> = HashSet()
        protected set

    @OneToMany(orphanRemoval = true)
    var needCertificateList: MutableList<CertificateUsage> = ArrayList()
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
            this.detailBusinessDescription,
            this.languageUsageList.map {
                LanguageResponse(
                    it.language.name
                )
            }.toSet(),
            this.technologyList.map {
                TechnologyResponse(
                    it.technology.name
                )
            }.toSet(),
            this.needCertificateList.map {
                 it.certificate.name
            },
            this.numberOfEmplyee,
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

    fun changeBigClassification(bigClassification: RecruitmentBigClassification) {
        this.bigClassification = bigClassification
    }

    fun changeSmallClassification(smallClassification: RecruitmentSmallClassification) {
        this.bigClassification = smallClassification.bigClassification
        this.smallClassification = smallClassification
    }

    fun removeNeedCertificate(certificateName: String) {
        this.needCertificateList.filter {
            it.certificate.name == certificateName
        }.map {
            this.needCertificateList.remove(it)
        }
    }

    fun removeTechnology(technologyName: String) {
        this.technologyList.filter {
            it.technology.name == technologyName
        }.map {
            this.technologyList.remove(it)
        }
    }

    fun removeLanguage(languageName: String) {
        this.languageUsageList.filter {
            it.language.name == languageName
        }.map {
            this.languageUsageList.remove(it)
        }
    }

}