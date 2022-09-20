package com.info.info_v1_backend.domain.company.data.entity.company

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyContactRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyInformationRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyNameRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.EditCompanyRequest
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse
import com.info.info_v1_backend.domain.company.data.entity.comment.Comment
import com.info.info_v1_backend.domain.company.data.entity.company.embeddable.CompanyContact
import com.info.info_v1_backend.domain.company.data.entity.company.embeddable.CompanyInformation
import com.info.info_v1_backend.domain.company.data.entity.company.embeddable.CompanyIntroduction
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.global.image.entity.File
import java.time.Year
import javax.persistence.*


@Entity
class Company(
    password: String,
    companyName: CompanyNameRequest,
    companyInformation: CompanyInformation,
    companyContact: CompanyContact,
    companyIntroduction: CompanyIntroduction,
): User(
    companyName.companyNumber,
    companyName.companyName,
    companyContact.email,
    password,
    Role.COMPANY
) {

    @Embedded
    var companyInformation: CompanyInformation = companyInformation
        protected set

    @Embedded
    var companyContact: CompanyContact = companyContact
        protected set


    @OneToMany(mappedBy = "company", cascade = [CascadeType.REMOVE])
    var businessTaggedList: MutableList<BusinessTagged> = ArrayList()
        protected set

    @Embedded
    var companyIntroduction: CompanyIntroduction = companyIntroduction
        protected set

    @OneToMany(
        cascade = [CascadeType.REMOVE],
        mappedBy = "company"
    )
    var commentList: MutableList<Comment> = ArrayList()


    @OneToMany(mappedBy = "company")
    var studentList: MutableList<Student> = ArrayList()
        protected set

    @OneToMany(mappedBy = "company")
    var noticeList: MutableList<Notice> = ArrayList()
        protected set

}