package com.info.info_v1_backend.domain.board.data.repository

import com.info.info_v1_backend.domain.board.data.entity.EmployBoard
import com.info.info_v1_backend.domain.board.data.entity.QEmployBoard
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Service

@Service
class BoardSupportImpl(
    private val query: JPAQueryFactory
): BoardSupport {
    override fun getLatestEmployBoard(): EmployBoard {
        return query.select(QEmployBoard.employBoard)
            .from(QEmployBoard.employBoard)
            .limit(1).fetchFirst()
    }

}