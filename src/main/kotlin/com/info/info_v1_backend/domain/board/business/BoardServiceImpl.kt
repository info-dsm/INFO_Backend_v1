package com.info.info_v1_backend.domain.board.business

import com.info.info_v1_backend.domain.board.business.dto.EmployBoardDto
import com.info.info_v1_backend.domain.board.data.repository.BoardRepository
import org.springframework.stereotype.Service

@Service
class BoardServiceImpl(
    private val boardRepository: BoardRepository
): BoardService {

    override fun getFullBoard(): EmployBoardDto {
        return boardRepository.getLatestEmployBoard().toEmployBoardDto()
    }

}