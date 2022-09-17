package com.info.info_v1_backend.domain.company.data.entity.notice.embeddable

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class ScreeningProcedure(
    @Column(name = "document_procedure")
    var document: Boolean,
    @Column(name = "technical_interview__procedure")
    var technicalInterview: Boolean,
    @Column(name = "physical_check__procedure")
    var physicalCheck: Boolean,
    @Column(name = "assignment_procedure")
    var assignment: Boolean,
    @Column(name = "executive_interview_procedure")
    var executiveInterview: Boolean,
    @Column(name = "else_procedure", length = 255)
    var elseProcedure: String?
) {
}