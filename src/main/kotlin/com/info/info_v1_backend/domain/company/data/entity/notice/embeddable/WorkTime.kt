package com.info.info_v1_backend.domain.company.data.entity.notice.embeddable

import javax.persistence.Column
import javax.persistence.Embeddable


@Embeddable
class WorkTime(
    untilCommuteStartTime: Int,
    untilCommuteEndTime: Int,
    workTimeForWeek: Int
) {
    @Column(name = "until_commute_start_time", nullable = false)
    var untilCommuteStartTime: Int = untilCommuteStartTime
        protected set

    @Column(name = "until_commute_end_time", nullable = false)
    var untilCommuteEndTime: Int = untilCommuteEndTime
        protected set

    @Column(name = "work_time_for_week", nullable = false)
    var workTimeForWeek: Int = workTimeForWeek


}