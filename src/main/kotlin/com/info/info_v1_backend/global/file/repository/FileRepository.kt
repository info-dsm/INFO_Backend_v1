package com.info.info_v1_backend.global.file.repository

import com.info.info_v1_backend.global.file.entity.File
import org.springframework.data.jpa.repository.JpaRepository

interface FileRepository<T: File>: JpaRepository<T, Long> {

}