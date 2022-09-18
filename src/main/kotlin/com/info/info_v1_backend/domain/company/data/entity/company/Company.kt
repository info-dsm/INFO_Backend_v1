package com.info.info_v1_backend.domain.company.data.entity.company

import com.info.info_v1_backend.domain.auth.data.entity.user.Contactor
import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.company.business.dto.request.company.EditCompanyRequest
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse
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
    address: String,
    companyPlace: String
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

    @Column(name = "company_address", nullable = false)
    var address: String = address
        protected set
    @Column(name = "company_place", nullable = false)
    var companyPlace: String = companyPlace

    fun hireStudentAll(studentList: List<Student>) {
        this.studentList.addAll(studentList)
    }
    fun editCompany(request: EditCompanyRequest) {
        request.shortName?. let {
            this.shortName = it
        }
        request.fullName?. let{
            this.fullName = it
        }
        request.companyPhone?. let{
            this.companyPhone = it
        }
        request.faxAddress?. let{
            this.faxAddress = it
        }
        request.establishedAt?. let{
            this.establishedAt = it
        }
        request.annualSales?. let{
            this.annualSales = it
        }
        request.workerCount?. let {
            this.workerCount = it
        }
        request.industryType?. let{
            this.industryType = it
        }
        request.mainProduct?. let{
            this.mainProduct = it
        }
        request.introduction?. let{
            this.introduction = it
        }
        request.address?. let{
            this.address = it
        }
        request.companyPlace?. let{
            this.companyPlace = it
        }
    }


    fun toMinimumCompanyResponse(): MinimumCompanyResponse {
        return MinimumCompanyResponse(
            this.id,
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
            this.id,
            this.shortName,
            this.fullName,
            this.photoList.map {
                it.toImageDto()
            },
            this.establishedAt,
            this.annualSales,
            this.introduction,
            this.employedCount,
            this.industryType,
            this.mainProduct,
            this.address,
            this.companyPlace,
            this.commentList.filter {
                !it.isDeleted
            }.map {
                it.toCommentResponse()
            }
        )
    }

    fun registerNotice(notice: Notice) {
        this.noticeList.add(notice)
    }

}