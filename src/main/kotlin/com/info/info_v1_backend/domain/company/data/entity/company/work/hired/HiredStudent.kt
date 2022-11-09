package com.info.info_v1_backend.domain.company.data.entity.company.work.hired

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.company.business.dto.response.company.HiredStudentResponse
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.company.work.field.FieldTraining
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.springframework.data.domain.Persistable
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.JoinColumns
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@IdClass(HiredStudentIdClass::class)
@Table(name = "hired_student")
@Where(clause = "hired_student_is_delete = false")
@SQLDelete(sql = "UPDATE `hired_student` SET hired_student_is_delete = true where student_id = ? AND company_id")
class HiredStudent(
    student: Student,
    company: Company,
    startDate: LocalDate,
    fieldTraining: FieldTraining?
): BaseTimeEntity(), Persistable<String>, java.io.Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    val student: Student = student

    @Id
    @ManyToOne
    @JoinColumn(name = "company_id")
    val company: Company = company

    @Column(name = "hired_start_date")
    var startDate: LocalDate = startDate
        protected set

    @OneToOne
    @JoinColumns(
        JoinColumn(
            name = "student_id",
            referencedColumnName = "student_id",
            insertable = false, updatable = false
        ),
        JoinColumn(
            name = "campany_id",
            referencedColumnName = "campany_id",
            insertable = false, updatable = false
        )
    )
    var fieldTraining: FieldTraining? = fieldTraining
        protected set

    @Column(name = "is_fire", nullable = false)
    var isFire: Boolean = false
        protected set

    @Column(name = "hired_student_is_delete", nullable = false)
    var isDelete: Boolean = false
        protected set


    fun makeFire() {
        this.isFire = true
    }

    override fun getId(): String? {
        return this.id
    }

    override fun isNew(): Boolean {
        return this.createdAt == null
    }

    fun toHiredStudentResponse(): HiredStudentResponse {
        return HiredStudentResponse(
            this.student.id!!,
            this.student.name,
            this.student.entranceYear - 2014,
            this.company.id!!,
            this.startDate
        )
    }

}