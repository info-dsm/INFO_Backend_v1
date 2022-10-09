package com.info.info_v1_backend.domain.company.data.entity.company.file

import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.global.file.dto.FileDto
import com.info.info_v1_backend.global.file.entity.File
import com.info.info_v1_backend.global.file.entity.type.FileType
import com.info.info_v1_backend.global.file.exception.FileShouldBeImageTypeException
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*


@Entity
@DiscriminatorValue("company_photo_file")
@OnDelete(action = OnDeleteAction.CASCADE)
class CompanyPhotoFile(
    dto: FileDto,
    company: Company
): File(
    dto.fileUrl,
    dto.fileType,
    dto.extension,
    dto.fileName
) {

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "company_id")
    var company: Company = company
        protected set

    init {
        if (dto.fileType == FileType.DOCS) throw FileShouldBeImageTypeException()
    }

}