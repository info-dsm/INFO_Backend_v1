package com.info.info_v1_backend.domain.company.data.entity.company

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import com.info.info_v1_backend.domain.auth.data.entity.user.Contactor
import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
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
){
    @Id
    val id: String = companyNumber

    @Column(name = "company_short_name", nullable = false)
    val shortName: String = shortName

    @Column(name = "company_full_name", nullable = false)
    val fullName: String = fullName

    @Column(name = "company_introduction", nullable = false)
    val introduction: String = introduction

    @OneToMany
    var photoList: MutableList<File> = ArrayList()

    @Column(name = "company_phone")
    var companyPhone: String = companyPhone

    @Column(name = "company_fax_address", nullable = true)
    var faxAddress: String? = faxAddress

    @OneToMany(mappedBy = "company")
    var contactorList: MutableList<Contactor> = ArrayList()

    @OneToMany
    var studentList: MutableList<Student> = ArrayList()
        protected set


    @Column(name = "company_established_at", nullable = false)
    var establishedAt: Year = establishedAt

    @Column(name = "company_annual_sales", nullable = false)
    var annualSales: Long = annualSales

    @Column(name = "company_worker_count", nullable = false)
    var workerCount: Int = workerCount

    @Column(name = "industry_type", nullable = true)
    var industryType: String? = industryType

    @Column(name = "main_product", nullable = true)
    var mainProduct: String? = mainProduct

    @OneToMany(mappedBy = "company", cascade = [CascadeType.REMOVE])
    var noticeList: MutableList<Notice> = ArrayList()

    init {
        this.contactorList.add(contactor)
    }


}