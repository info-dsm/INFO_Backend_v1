package com.info.info_v1_backend.domain.company.data.entity.company.embeddable

import com.info.info_v1_backend.domain.company.business.dto.response.company.CompanyIntroductionResponse
import com.info.info_v1_backend.domain.company.data.entity.company.file.BusinessRegisteredCertificateFile
import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyIntroductionFile
import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyLogoFile
import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyPhotoFile
import com.info.info_v1_backend.global.file.entity.File
import javax.persistence.CascadeType
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Embeddable
class CompanyIntroduction(
    introduction: String,
) {

    var introduction: String = introduction
        protected set

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(nullable = true)
    var businessRegisteredCertificate: BusinessRegisteredCertificateFile? = null
        protected set

    @OneToMany(cascade = [CascadeType.PERSIST])
    var companyIntroductionFile: MutableList<CompanyIntroductionFile> = ArrayList()
        protected set

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(nullable = true)
    var companyLogo: CompanyLogoFile? = null
        protected set

    @OneToMany(cascade = [CascadeType.PERSIST])
    var companyPhotoList: MutableList<CompanyPhotoFile> = ArrayList()
        protected set


    fun addCompanyIntroductionFile(file: CompanyIntroductionFile) {
        this.companyIntroductionFile.add(file)
    }


    fun registerCompanyLogoAndBusinessCertificate(logo: CompanyLogoFile, businessRegisteredCertificate: BusinessRegisteredCertificateFile) {
        this.companyLogo = logo
        this.businessRegisteredCertificate = businessRegisteredCertificate
    }

    fun changeBusinessRegisteredCertificate(file: BusinessRegisteredCertificateFile) {
        this.businessRegisteredCertificate = file
    }

    fun addCompanyIntroduction(file: CompanyIntroductionFile) {
        this.companyIntroductionFile.add(file)
    }

    fun removeCompanyIntroduction(file: CompanyIntroductionFile) {
        this.companyIntroductionFile.remove(file)
    }

    fun changeCompanyLogo(file: CompanyLogoFile) {
        this.companyLogo = file
    }

    fun addCompanyPhoto(file: CompanyPhotoFile) {
        this.companyPhotoList.add(
            file
        )
    }

    fun removeCompanyPhoto(file: CompanyPhotoFile) {
        this.companyPhotoList.remove(file)
    }

    fun toCompanyIntroductionResponse(): CompanyIntroductionResponse {
        return CompanyIntroductionResponse(
            introduction = this.introduction,
            companyIntroductionFile = this.companyIntroductionFile.map {
                it.toFileResponse()
            }.toList(),
            companyLogo = this.companyLogo?.toFileResponse(),
            companyPhotoList = this.companyPhotoList.map {
                it.toFileResponse()
            }.toList()
        )
    }

    fun getBusinessRegisteredCertificateResponse(): BusinessRegisteredCertificateFile {
        return this.businessRegisteredCertificate!!
    }

    fun editCompanyIntroduction(introduction: String) {
        this.introduction = introduction
    }

}