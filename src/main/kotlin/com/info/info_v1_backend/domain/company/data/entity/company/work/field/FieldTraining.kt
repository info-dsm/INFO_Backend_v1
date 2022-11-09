package com.info.info_v1_backend.domain.company.data.entity.company.work.field

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.company.business.dto.response.company.FieldTrainingResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.FieldTrainingStudentWithHiredResponse
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.company.work.hired.HiredStudent
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
@IdClass(FieldTrainingIdClass::class)
@Table(name = "field_training")
@Where(clause = "field_training_is_delete = false")
@SQLDelete(sql = "UPDATE `field_training` SET field_training_is_delete = true where student_id = ? AND company_id = ?")
class FieldTraining(
    student: Student,
    company: Company,
    startDate: LocalDate,
    endDate: LocalDate
): BaseTimeEntity(), Persistable<String>, java.io.Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false, insertable = false, updatable = false)
    val student: Student = student

    @Id
    @ManyToOne
    @JoinColumn(name = "campany_id", nullable = false)
    val company: Company = company

    @Column(name = "field_training_start_date", nullable = false)
    val startDate: LocalDate = startDate

    @Column(name = "field_training_end_date", nullable = false)
    var endDate: LocalDate = endDate
        protected set

    @OneToOne
    @JoinColumns(
        JoinColumn(
            name = "student_id",
            referencedColumnName = "student_id",
            insertable = false, updatable = false
        ),
        JoinColumn(
            name = "company_id",
            referencedColumnName = "company_id",
            insertable = false, updatable = false
        )
    )
    var hiredStudent: HiredStudent? = null
        protected set

    @Column(name = "field_training_is_linked")
    var isLinked: Boolean = false
        protected set

    @Column(name = "field_training_is_delete", nullable = false)
    var isDelete: Boolean = false
        protected set

    override fun getId(): String? {
        return this.id
    }

    override fun isNew(): Boolean {
        return this.createdAt == null
    }

    fun makeNoLinked() {
        this.isLinked = false
    }



    fun toHiredStudent(startDate: LocalDate): HiredStudent {
        this.isLinked = true
        this.endDate = startDate
        return HiredStudent(
            this.student,
            this.company,
            startDate,
            this
        )
    }

    fun toFieldTrainingResponse(): FieldTrainingResponse {
        return FieldTrainingResponse(
            this.student.id!!,
            this.student.name,
            this.student.studentKey,
            this.company.id!!,
            this.startDate,
            this.endDate,
            this.hiredStudent?. let {
                return@let true
            }?: false
        )
    }

    fun toFieldTrainingStudentWithHiredResponse(): FieldTrainingStudentWithHiredResponse {
        return FieldTrainingStudentWithHiredResponse(
            this.student.id!!,
            this.student.name,
            this.student.studentKey,
            this.company.id!!,
            this.startDate,
            this.endDate,
            this.hiredStudent?. let {
                return@let true
            }?: false,
            this.hiredStudent?.toHiredStudentResponse()
        )
    }

}