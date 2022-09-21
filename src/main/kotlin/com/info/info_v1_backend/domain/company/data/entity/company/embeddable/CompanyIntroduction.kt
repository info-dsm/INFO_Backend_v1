package com.info.info_v1_backend.domain.company.data.entity.company.embeddable

import com.info.info_v1_backend.global.file.entity.File
import javax.persistence.Embeddable
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Embeddable
class CompanyIntroduction(
    introduction: String,
    businessRegisteredCertificate: File,
    companyIntroductionFile: File?,
    companyLogo: File?,
    companyPhotoList: List<File>
) {

    var introduction: String = introduction
        protected set

    @OneToOne
    var businessRegisteredCertificate: File = businessRegisteredCertificate
        protected set

    @OneToOne
    var companyIntroductionFile: File? = companyIntroductionFile
        protected set

    @OneToOne
    var companyLogo: File? = companyLogo
        protected set

    @OneToMany(mappedBy = "company")
    var companyPhotoList: MutableList<File> = ArrayList()
        protected set

}