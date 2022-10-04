package com.info.info_v1_backend.domain.company.data.entity.company.file

import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.global.file.dto.FileDto
import com.info.info_v1_backend.global.file.entity.File
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToOne


@Entity
@DiscriminatorValue("company_logo_file")
@Where(clause = "file_is_deleted = false")
@SQLDelete(sql = "UPDATE `file` SET file_is_deleted = true where id = ?")
class CompanyLogoFile(
    dto: FileDto,
    company: Company
): File(
    dto.fileUrl,
    dto.fileType,
    dto.extension,
    dto.fileName
) {

    @OneToOne
    @JoinColumn(name = "company_id", nullable = false)
    var company: Company = company
        protected set

    override fun toString(): String {
        return "url: ${this.fileUrl}, companyId: ${this.company.id!!}, fileType: ${this.fileContentType}, " +
                "extension: ${this.extension}"
    }

}