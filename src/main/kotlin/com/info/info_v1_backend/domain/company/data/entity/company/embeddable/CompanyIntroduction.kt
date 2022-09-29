package com.info.info_v1_backend.domain.company.data.entity.company.embeddable

import com.info.info_v1_backend.domain.company.business.dto.response.company.CompanyIntroductionResponse
import com.info.info_v1_backend.domain.company.data.entity.company.file.BusinessRegisteredCertificateFile
import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyIntroductionFile
import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyLogoFile
import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyPhotoFile
import com.info.info_v1_backend.global.file.entity.File
import javax.persistence.Embeddable
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Embeddable
class CompanyIntroduction(
    introduction: String,
//    businessRegisteredCertificate: BusinessRegisteredCertificateFile,
//    companyIntroductionFile: MutableList<CompanyIntroductionFile>,
//    companyLogo: CompanyLogoFile?,
//    companyPhotoList: MutableList<CompanyPhotoFile>
) {

    var introduction: String = introduction
        protected set

    @OneToOne
    var businessRegisteredCertificate: BusinessRegisteredCertificateFile? = null
        protected set

    @OneToMany
    var companyIntroductionFile: MutableList<CompanyIntroductionFile> = ArrayList()
        protected set

    @OneToOne
    var companyLogo: CompanyLogoFile? = null
        protected set

    @OneToMany(mappedBy = "company")
    var companyPhotoList: MutableList<CompanyPhotoFile> = ArrayList()
        protected set


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
            this.introduction,
            this.businessRegisteredCertificate?.toFileResponse(),
            this.companyIntroductionFile.map {
                it.toFileResponse()
            },
            this.companyLogo?.toFileResponse(),
            this.companyPhotoList.map {
                it.toFileResponse()
            }
        )
    }

}