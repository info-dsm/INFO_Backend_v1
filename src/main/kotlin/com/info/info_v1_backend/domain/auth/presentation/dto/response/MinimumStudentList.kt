package com.info.info_v1_backend.domain.auth.presentation.dto.response

data class MinimumStudentList(
        val miniStudentList: List<MinimumStudent>
) {
    data class MinimumStudent(

            val name: String,
            val studentKey: String,
            val id: Long
            )
}

