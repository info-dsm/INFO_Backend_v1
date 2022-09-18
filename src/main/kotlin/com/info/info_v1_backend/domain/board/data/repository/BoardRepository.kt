package com.info.info_v1_backend.domain.board.data.repository

import com.info.info_v1_backend.domain.board.data.entity.EmployBoard
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository: JpaRepository<EmployBoard, Long>, BoardSupport {

}