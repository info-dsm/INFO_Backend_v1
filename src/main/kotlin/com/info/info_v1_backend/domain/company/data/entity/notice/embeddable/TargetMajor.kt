package com.info.info_v1_backend.domain.company.data.entity.notice.embeddable

import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.type.MajorType
import javax.persistence.Embeddable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


@Entity
class TargetMajor(
    major: MajorType,
    cnt: Int,
    notice: Notice
) {
    @Id
    val id: Long? = null

    @ManyToOne
    @JoinColumn(name = "target_notice_major")
    var notice: Notice = notice

    var major: MajorType = major

    var cnt = cnt
        protected set


}