package com.info.info_v1_backend.domain.auth.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.business.dto.response.MinimumStudent
import com.info.info_v1_backend.domain.auth.business.dto.response.UserInfoResponse
import org.springframework.data.domain.Page

interface UserService {

    fun deleteMe(user: User)
    fun getUserInfo(user: User, userId: Long?): UserInfoResponse

    fun getStudentList(user: User, idx: Int, size: Int): Page<MinimumStudent>


}