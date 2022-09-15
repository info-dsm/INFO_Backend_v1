package com.info.info_v1_backend.domain.company.data.entity.notice.embeddable

import com.info.info_v1_backend.domain.company.data.entity.type.MajorType
import javax.persistence.Embeddable


@Embeddable
class TargetMajor(
    major: MajorType,
    cnt: Int
) {

    var major: MajorType = major

    var cnt = cnt
        protected set


}