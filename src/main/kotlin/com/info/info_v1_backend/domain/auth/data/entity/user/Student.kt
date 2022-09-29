package com.info.info_v1_backend.domain.auth.data.entity.user

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import com.info.info_v1_backend.domain.auth.business.dto.request.EditStudentInfoRequest
import com.info.info_v1_backend.domain.auth.business.dto.response.MinimumStudent
import com.info.info_v1_backend.domain.auth.business.dto.response.StudentInfoResponse
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.company.work.field.FieldTraining
import com.info.info_v1_backend.domain.company.data.entity.company.work.hired.HiredStudent
import com.info.info_v1_backend.domain.project.data.entity.Creation
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
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

    @OneToMany(mappedBy = "student", cascade = [CascadeType.REMOVE])
    var creationList: MutableList<Creation>? = creationList

    @OneToMany(mappedBy = "student")
    var hiredStudent: MutableList<HiredStudent> = ArrayList()
        protected set

    @OneToMany(mappedBy = "student")
    var fieldTraining: MutableList<FieldTraining> = ArrayList()
        protected set

    fun toMinimumStudent(): MinimumStudent {
        return MinimumStudent(
                this.name,
                this.studentKey,
                this.id!!
        )
    }

    fun toStudentInfoResponse(): StudentInfoResponse{
        return StudentInfoResponse(
            this.name,
            this.email,
            this.studentKey,
            this.hiredStudent.isNotEmpty(),
            this.hiredStudent.first().company.toMinimumCompanyResponse()
        )

    }



}