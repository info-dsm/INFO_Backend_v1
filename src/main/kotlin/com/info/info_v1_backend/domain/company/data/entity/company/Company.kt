package com.info.info_v1_backend.domain.company.data.entity.company

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyNameRequest
import com.info.info_v1_backend.domain.company.data.entity.comment.Comment
import com.info.info_v1_backend.domain.company.data.entity.company.embeddable.CompanyContact
import com.info.info_v1_backend.domain.company.data.entity.company.embeddable.CompanyInformation
import com.info.info_v1_backend.domain.company.data.entity.company.embeddable.CompanyIntroduction
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import javax.persistence.*


@Entity
class Company(
    password: String,
    companyName: CompanyNameRequest,
    companyInformation: CompanyInformation,
    companyContact: CompanyContact,
    companyIntroduction: CompanyIntroduction,
    isLeading: Boolean,

): User(
    companyName.companyName,
    companyContact.email,
    password,
    Role.COMPANY
) {

    @Column(name = "company_number")
    var companyNumber: String = companyName.companyNumber
        protected set

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

    @Column(name = "company_is_leading", nullable = false)
    var isLeading: Boolean = isLeading
        protected set


    @OneToMany(mappedBy = "company")
    var studentList: MutableList<Student> = ArrayList()
        protected set

    @OneToMany(mappedBy = "company")
    var noticeList: MutableList<Notice> = ArrayList()
        protected set

}