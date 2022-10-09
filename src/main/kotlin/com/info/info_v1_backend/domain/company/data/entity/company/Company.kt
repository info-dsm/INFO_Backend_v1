package com.info.info_v1_backend.domain.company.data.entity.company

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyNameRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.EditCompanyRequest
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyWithIsWorkingResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.NoticeWithIsApproveResponse
import com.info.info_v1_backend.domain.company.data.entity.comment.Comment
import com.info.info_v1_backend.domain.company.data.entity.company.embeddable.CompanyContact
import com.info.info_v1_backend.domain.company.data.entity.company.embeddable.CompanyInformation
import com.info.info_v1_backend.domain.company.data.entity.company.embeddable.CompanyIntroduction
import com.info.info_v1_backend.domain.company.data.entity.company.work.hired.HiredStudent
import com.info.info_v1_backend.domain.company.data.entity.company.tag.BusinessAreaTagged
import com.info.info_v1_backend.domain.company.data.entity.company.work.field.FieldTraining
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDate
import java.time.Year
import javax.persistence.*


@Entity
@DiscriminatorValue("company")
@OnDelete(action = OnDeleteAction.CASCADE)
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

    @ElementCollection
    var noticeRegisteredYearList: MutableList<Int> = ArrayList()
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


    fun updateLastNoticeYear() {
        if (!this.noticeRegisteredYearList.contains(
            LocalDate.now().year
        )) this.noticeRegisteredYearList.add(LocalDate.now().year)
    }

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
            this.noticeList.lastOrNull()?.createdAt?.year?. let {Year.of(it)},
            this.hiredStudentList.filter {
                !it.isFire
            }.size,
            this.commentList.map {
                it.toCommentResponse()
            },
            this.companyIntroduction.toCompanyIntroductionResponse(),
        )
    }

    fun toMaximumCompanyResponse(): MaximumCompanyResponse {
        return MaximumCompanyResponse(
            companyId = this.id!!,
            companyNumber = this.companyNumber,
            companyName = this.name,
            companyInformation = this.companyInformation.toCompanyInformationRequest(),
            companyContact = this.companyContact.toCompanyContactRequest(),
            businessAreaResponseList = this.businessAreaTaggedList.map {
                it.businessArea.toBusinessAreaResponse()
            },
            companyIntroduction = this.companyIntroduction.toCompanyIntroductionResponse(),
            commentList = this.commentList.map {
                it.toCommentResponse()
            },
            isLeading = this.isLeading,
            isAssociated = this.isAssociated,
            lastNoticeDate = this.noticeList.lastOrNull()
                ?.createdAt?.toLocalDate(),
            totalHiredStudentList = this.hiredStudentList.map {
                it.toHiredStudentResponse()
            }
        )
    }

    fun toMaximumCompanyWithIsWorkingResponse(): MaximumCompanyWithIsWorkingResponse {
        return MaximumCompanyWithIsWorkingResponse(
            this.toMaximumCompanyResponse(),
            this.fieldTrainingList.filter {
                !it.isDelete && !it.isLinked
            }.firstOrNull()?.let {
                return@let true
            }?: this.hiredStudentList.filter {
                !it.isFire && !it.isDelete
            }.firstOrNull()?. let {
                return@let true
            }?: false
        )
    }

    fun editCompany(r: EditCompanyRequest) {
        r.companyName?.let {
            this.name = r.companyName
        }
        r.companyInformation?.let {
            this.companyInformation.editCompanyInformation(it)
        }
        r.companyContact?.let {
            this.companyContact.editCompanyContact(it)
        }
        r.introduction?.let {
            this.companyIntroduction.editCompanyIntroduction(it)
        }
        r.isLeading?.let {
            this.isLeading = it
        }
    }

    fun addBusinessAreaTagged(businessAreaTagged: BusinessAreaTagged) {
        this.businessAreaTaggedList.add(businessAreaTagged)
    }


}