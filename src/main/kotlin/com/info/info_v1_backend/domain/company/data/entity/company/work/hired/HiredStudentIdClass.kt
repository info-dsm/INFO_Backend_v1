package com.info.info_v1_backend.domain.company.data.entity.company.work.hired

class HiredStudentIdClass(
    studentId: Long,
    companyId: Long
): java.io.Serializable {
    val student: Long = studentId
    val company: Long = companyId
}