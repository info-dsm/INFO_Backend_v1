package com.info.info_v1_backend.domain.company.data.entity.company

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import com.info.info_v1_backend.domain.auth.data.entity.user.Contactor
import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.EditCompanyRequest
import com.info.info_v1_backend.domain.company.business.dto.response.MaximumCompanyResponse
import com.info.info_v1_backend.domain.company.business.dto.response.MinimumCompanyResponse
import com.info.info_v1_backend.domain.company.data.entity.comment.Comment
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import com.info.info_v1_backend.global.image.entity.File
import java.time.Year
import javax.persistence.*


@Entity
class Company(
    shortName: String,
    fullName: String,
    companyNumber: String,
    companyPhone: String,
    faxAddress: String?,
    contactor: Contactor,
    establishedAt: Year,
    annualSales: Long,
    workerCount: Int,
    industryType: String?,
    mainProduct: String?,
    introduction: String,
): BaseTimeEntity(){
    @Id
    val id: String = companyNumber

    @Column(name = "company_short_name", nullable = false)
    var shortName: String = shortName
        protected set

    @Column(name = "company_full_name", nullable = false)
    var fullName: String = fullName
        protected set

    @Column(name = "company_introduction", nullable = false)
    var introduction: String = introduction
        protected set

    @OneToMany
    var photoList: MutableList<File> = ArrayList()
        protected set

    @Column(name = "company_phone")
    var companyPhone: String = companyPhone
        protected set

    @Column(name = "company_fax_address", nullable = true)
    var faxAddress: String? = faxAddress
        protected set

    @OneToOne
    var contactor: Contactor = contactor
        protected set

    @OneToMany
    var studentList: MutableList<Student> = ArrayList()
        protected set

    @Column(name = "company_established_at", nullable = false)
    var establishedAt: Year = establishedAt
        protected set

    @Column(name = "company_annual_sales", nullable = false)
    var annualSales: Long = annualSales
        protected set

    @Column(name = "company_worker_count", nullable = false)
    var workerCount: Int = workerCount
        protected set

    @Column(name = "industry_type", nullable = true)
    var industryType: String? = industryType
        protected set

    @Column(name = "main_product", nullable = true)
    var mainProduct: String? = mainProduct
        protected set

    @OneToMany(mappedBy = "company", cascade = [CascadeType.REMOVE])
    var noticeList: MutableList<Notice> = ArrayList()
        protected set

    @Column(name = "employed_count", nullable = false)
    var employedCount: Int = 0

    @OneToMany(
        cascade = [CascadeType.REMOVE],
        mappedBy = "company"
    )
    var commentList: MutableList<Comment> = ArrayList()

    fun editCompany(request: EditCompanyRequest) {
        this.shortName = request.shortName
        this.fullName = request.fullName
        this.companyPhone = request.companyPhone
        this.faxAddress = request.faxAddress
        this.establishedAt = request.establishedAt
        this.annualSales = request.annualSales
        this.workerCount = request.workerCount
        this.industryType = request.industryType
        this.mainProduct = request.mainProduct
        this.introduction = request.introduction
    }


    fun toMinimumCompanyResponse(): MinimumCompanyResponse {
        return MinimumCompanyResponse(
            this.shortName,
            this.fullName,
            this.photoList.map {
                it.toImageDto()
            },
            this.introduction
        )
    }

    fun toMaximumCompanyResponse(): MaximumCompanyResponse {
        return MaximumCompanyResponse(
            this.shortName,
            this.fullName,
            this.photoList.map {
                it.toImageDto()
            },
            this.introduction,
            this.employedCount,
            this.commentList.filter {
                !it.isDeleted
            }.map {
                it.toCommentResponse()
            }
        )
    }

}