package com.info.info_v1_backend.domain.board.business

import com.info.info_v1_backend.domain.board.business.dto.EmployBoardDto

interface BoardService {

    fun getFullBoard(): EmployBoardDto
}