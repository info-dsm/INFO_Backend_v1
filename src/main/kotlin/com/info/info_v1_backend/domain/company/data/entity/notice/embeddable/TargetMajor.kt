package com.info.info_v1_backend.domain.company.data.entity.notice.embeddable

import com.info.info_v1_backend.domain.company.business.dto.request.notice.TargetMajorRequest
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.type.MajorType
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import javax.persistence.Embeddable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


@Entity
class TargetMajor(
    major: MajorType,
    count: Int,
    notice: Notice
): BaseTimeEntity() {
    @Id
    val id: Long? = null

    @ManyToOne
    @JoinColumn(name = "target_notice_major")
    var notice: Notice = notice

    var major: MajorType = major

    var count = count
        protected set

    fun editTargetMajor(r: TargetMajorRequest) {
        this.major = r.majorType
        this.count = r.count
    }


}