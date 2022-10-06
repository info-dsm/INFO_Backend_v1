package com.info.info_v1_backend.domain.company.data.entity.company.file

import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.global.file.dto.FileDto
import com.info.info_v1_backend.global.file.entity.File
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*


@Entity
@DiscriminatorValue("business_registered_certificate_file")
@OnDelete(action = OnDeleteAction.CASCADE)
class BusinessRegisteredCertificateFile(
    dto: FileDto,
    company: Company
): File(
    dto.fileUrl,
    dto.fileType,
    dto.extension,
    dto.fileName
){

    @OneToOne
    @JoinColumn(name = "company_id")
    var company: Company = company
        protected set

}