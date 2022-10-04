package com.info.info_v1_backend.domain.company.data.entity.company.file

import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.global.file.dto.FileDto
import com.info.info_v1_backend.global.file.entity.File
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@DiscriminatorValue("company_introduction_file")
@Where(clause = "file_is_deleted = false")
@SQLDelete(sql = "UPDATE `file` SET file_is_deleted = true where id = ?")
class CompanyIntroductionFile(
    dto: FileDto,
    company: Company
): File(
    dto.fileUrl,
    dto.fileType,
    dto.extension,
    dto.fileName
) {

    @ManyToOne
    @JoinColumn(name = "company_id")
    var company: Company = company
        protected set

}