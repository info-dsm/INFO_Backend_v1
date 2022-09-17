package com.info.info_v1_backend.domain.company.data.entity.company

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.global.image.entity.File
import java.time.Year
import java.util.Date
import javax.persistence.*


@Entity
@DiscriminatorValue("company")
@Inheritance(strategy = InheritanceType.JOINED)
class Company(
    shortName: String,
    fullName: String,
    companyNumber: String,
    contactor: Contactor,
    establishedAt: Year,
    annualSales: Long,
    workerCount: Int,
    industryType: String?,
    mainProduct: String?,
    introduction: String,
): User(
    fullName,
    contactor.name,
    contactor.password,
    Role.COMPANY
){

    @Column(name = "company_short_name", nullable = false)
    val shortName: String = shortName

    @Column(name = "company_introduction", nullable = false)
    val introduction: String = introduction

    @OneToMany
    var photoList: MutableList<File> = ArrayList()

    @Embedded
    var contactor: Contactor = contactor

    @OneToMany
    var studentList: MutableList<Student> = ArrayList()
        protected set

    @Column(name = "company_number", nullable = false)
    var companyNumber: String = companyNumber

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


}