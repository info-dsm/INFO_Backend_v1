package com.info.info_v1_backend.domain.auth.data.entity.user

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import com.info.info_v1_backend.domain.auth.business.dto.request.EditStudentInfoRequest
import com.info.info_v1_backend.domain.auth.business.dto.response.MinimumStudent
import com.info.info_v1_backend.domain.auth.business.dto.response.StudentInfoResponse
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.company.work.field.FieldTraining
import com.info.info_v1_backend.domain.company.data.entity.company.work.hired.HiredStudent
import com.info.info_v1_backend.domain.company.data.entity.notice.applicant.Applicant
import com.info.info_v1_backend.domain.project.data.entity.Creation
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.Year
import javax.persistence.*


@Entity
@DiscriminatorValue("student")
@OnDelete(action = OnDeleteAction.CASCADE)
class Student(
    studentKey: String,
    name: String,
    email: String,
    password: String,
    creationList: MutableList<Creation>?,
): User(
    name,
    email,
    password,
    Role.STUDENT
) {
    val studentKey: String = studentKey

    val entranceYear: Year = Year.now().minusYears((studentKey.substring(0, 1).toLong()-1))

    @OneToMany(mappedBy = "student", cascade = [CascadeType.REMOVE])
    var creationList: MutableList<Creation>? = creationList

    @OneToMany(mappedBy = "student")
    var hiredStudentList: MutableList<HiredStudent> = ArrayList()
        protected set

    @OneToMany(mappedBy = "student")
    var fieldTrainingList: MutableList<FieldTraining> = ArrayList()
        protected set

    @OneToMany(mappedBy = "student")
    var applicantList: MutableList<Applicant> = ArrayList()
        protected set

    fun toMinimumStudent(): MinimumStudent {
        return MinimumStudent(
                this.name,
                this.studentKey,
                this.id!!
        )
    }

    fun toStudentInfoResponse(): StudentInfoResponse {
        return StudentInfoResponse(
            this.name,
            this.email,
            this.studentKey,
            this.fieldTrainingList.filter {
                !it.isDelete || !it.isLinked
            }.isNotEmpty(),
            this.hiredStudentList.filter {
                !it.isFire || !it.isDelete
            }.isNotEmpty(),
            this.hiredStudentList.filter {
                !it.isFire || !it.isDelete
            }.firstOrNull()?.company?.toMinimumCompanyResponse()
        )
    }



}