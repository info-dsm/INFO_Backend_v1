package com.info.info_v1_backend.domain.company.data.entity.company

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyNameRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.EditCompanyRequest
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse
import com.info.info_v1_backend.domain.company.data.entity.comment.Comment
import com.info.info_v1_backend.domain.company.data.entity.company.embeddable.CompanyContact
import com.info.info_v1_backend.domain.company.data.entity.company.embeddable.CompanyInformation
import com.info.info_v1_backend.domain.company.data.entity.company.embeddable.CompanyIntroduction
import com.info.info_v1_backend.domain.company.data.entity.company.work.hired.HiredStudent
import com.info.info_v1_backend.domain.company.data.entity.company.tag.BusinessAreaTagged
import com.info.info_v1_backend.domain.company.data.entity.company.work.field.FieldTraining
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDate
import java.time.Year
import javax.persistence.*


@Entity
@DiscriminatorValue("company")
@Where(clause = "company_is_delete = false")
@SQLDelete(sql = "UPDATE `company` SET company_is_delete = true where id = ?")
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
    var businessAreaTaggedList: MutableList<BusinessAreaTagged> = ArrayList()
        protected set

    @Embedded
    var companyIntroduction: CompanyIntroduction = companyIntroduction
        protected set

    @OneToMany(
        cascade = [CascadeType.REMOVE],
        mappedBy = "company"
    )
    var commentList: MutableList<Comment> = ArrayList()
        protected set

    @Column(name = "company_is_leading", nullable = false)
    var isLeading: Boolean = isLeading
        protected set

    @OneToMany(mappedBy = "company")
    var noticeList: MutableList<Notice> = ArrayList()
        protected set

    @OneToMany(mappedBy = "company")
    var hiredStudentList: MutableList<HiredStudent> = ArrayList()
        protected set

    @OneToMany(mappedBy = "company")
    var fieldTrainingList: MutableList<FieldTraining> = ArrayList()
        protected set

    @Column(name = "company_is_associated", nullable = false)
    var isAssociated: Boolean = false
        protected set

    @Column(name = "company_is_delete", nullable = false)
    var isDelete: Boolean = false
        protected set

    fun makeAssociated() {
        this.isAssociated = true
    }

    fun toMinimumCompanyResponse(): MinimumCompanyResponse {
        return MinimumCompanyResponse(
            this.id!!,
            this.companyNumber,
            this.companyContact.email,
            this.name,
            this.companyInformation.homeAddress,
            this.businessAreaTaggedList.map {
                it.businessArea.toBusinessAreaResponse()
            },
            this.hiredStudentList.size,
            this.companyInformation.annualSales,
            this.isLeading,
            this.isAssociated,
            Year.of(this.noticeList.last().createdAt?.year ?: LocalDate.now().year),
            this.hiredStudentList.filter {
                !it.isFire
            }.map {
                it.toHiredStudentResponse()
            },
            this.commentList.map {
                it.toCommentResponse()
            }
        )
    }

    fun toMaximumCompanyResponse(): MaximumCompanyResponse {
        return MaximumCompanyResponse(
            this.id!!,
            this.companyNumber,
            this.name,
            this.companyInformation.toCompanyInformationRequest(),
            this.companyContact.toCompanyContactRequest(),
            this.businessAreaTaggedList.map {
                it.businessArea.toBusinessAreaResponse()
            },
            this.companyIntroduction.toCompanyIntroductionResponse(),
            this.commentList.map {
                it.toCommentResponse()
            },
            this.isLeading,
            this.noticeList.map {
                it.toMinimumNoticeResponse()
            },
            this.hiredStudentList.filter {
                !it.isFire
            }.map {
                it.toHiredStudentResponse()
            },
            this.fieldTrainingList.filter {
                it.endDate.isAfter(LocalDate.now())
            }.map {
                it.toFieldTrainingResponse()
            },
            this.isAssociated
        )
    }

    fun editCompany(r: EditCompanyRequest) {

    }

}