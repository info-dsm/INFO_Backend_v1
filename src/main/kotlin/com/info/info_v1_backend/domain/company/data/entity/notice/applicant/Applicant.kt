package com.info.info_v1_backend.domain.company.data.entity.notice.applicant

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.company.business.dto.response.notice.ApplicantResponse
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.notice.file.Reporter
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.springframework.data.domain.Persistable
import java.io.Serializable
import javax.persistence.*


@Entity
@IdClass(ApplicantIdClass::class)
@Table(name = "applicant")
@SQLDelete(sql = "UPDATE `applicant` SET applicant_is_delete = true WHERE student_id = ? AND notice_id = ?")
@Where(clause = "applicant_is_delete = false")
class Applicant(
    student: Student,
    notice: Notice
): BaseTimeEntity(), Persistable<ApplicantIdClass>, Serializable {

    @Id
    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "student_id")
    val student: Student = student

    @Id
    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "notice_id")
    val notice: Notice = notice

    @Column(name = "applicant_is_delete", nullable = false)
    var isDelete: Boolean = false
        protected set

    @OneToMany(mappedBy = "applicant", cascade = [CascadeType.PERSIST])
    var reporterList: MutableList<Reporter> = ArrayList()
        protected set

    override fun getId(): ApplicantIdClass {
        return ApplicantIdClass(notice.id, student.id);
    }

    override fun isNew(): Boolean {
        return this.createdAt == null
    }

    fun toApplicantResponse(): ApplicantResponse {
        return ApplicantResponse(
            this.student.toMinimumStudent(),
            this.reporterList
        )
    }

}