package com.info.info_v1_backend.domain.board.data.repository

import com.info.info_v1_backend.domain.board.data.entity.EmployBoard

interface BoardSupport {

    fun getLatestEmployBoard(): EmployBoard
}