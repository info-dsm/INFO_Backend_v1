package com.info.info_v1_backend.domain.company.data.entity.notice.embeddable

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class CommuteTime(
    startTime: Int,
    endTime: Int
) {
    @Column(name = "start_time", nullable = false)
    var startTime = startTime

    @Column(name = "end_time", nullable = false)
    var endTime = endTime


}