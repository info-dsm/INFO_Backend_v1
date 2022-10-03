package com.info.info_v1_backend.domain.auth.presentation.controller

import com.info.info_v1_backend.domain.auth.business.dto.response.MinimumStudent
import com.info.info_v1_backend.domain.auth.business.dto.response.StudentInfoResponse
import com.info.info_v1_backend.domain.auth.business.dto.response.UserInfoResponse
import com.info.info_v1_backend.domain.auth.business.service.UserService
import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.exception.UserNotFoundException
import com.info.info_v1_backend.global.error.common.TokenNotFoundException
import org.springframework.data.domain.Page
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/info/v1/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/info")
    fun getMyInfo(
        @AuthenticationPrincipal user: User?,
        @RequestParam(required = false) userId: Long?
    ): UserInfoResponse {
        userId?. let {
            return userService.getUserInfo(
                user?: throw TokenNotFoundException(),
                it
            )
        }?: return userService.getUserInfo(
            user?: throw TokenNotFoundException(),
            null
        )
    }

    @GetMapping("/get-student-list")
    fun getStudentList(
        @AuthenticationPrincipal user: User?,
        @RequestParam idx: Int,
        @RequestParam size: Int
    ): Page<MinimumStudent> {
        return userService.getStudentList(
            user?: throw TokenNotFoundException(),
            idx,
            size
        )
    }

}