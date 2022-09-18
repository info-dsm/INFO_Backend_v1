package com.info.info_v1_backend.global.image.repository

import com.info.info_v1_backend.global.image.entity.File
import org.springframework.data.jpa.repository.JpaRepository

interface FileRepository: JpaRepository<File, Long> {

}