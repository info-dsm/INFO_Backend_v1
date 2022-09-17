package com.info.info_v1_backend.domain.board.presentation

import com.info.info_v1_backend.domain.board.business.BoardService
import com.info.info_v1_backend.domain.board.business.dto.EmployBoardDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/info/v1/board")
class BoardController(
    private val boardService: BoardService
) {

    @GetMapping("/employment")
    fun getEmploymentBoard(): EmployBoardDto {
        return boardService.getFullBoard()
    }

}