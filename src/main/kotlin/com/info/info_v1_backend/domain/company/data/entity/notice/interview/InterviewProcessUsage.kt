package com.info.info_v1_backend.domain.company.data.entity.notice.interview

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class InterviewProcessUsage(
    sequence: Int,
    interviewProcess: InterviewProcess
) {

    @Column(name = "sequence", nullable = false)
    var sequence: Int = sequence
        protected set

    @Column(name = "interview_process", nullable = false)
    var interviewProcess = interviewProcess
        protected set

    fun changeInterviewProcess(interviewProcess: InterviewProcess) {
        this.interviewProcess = interviewProcess
    }

}