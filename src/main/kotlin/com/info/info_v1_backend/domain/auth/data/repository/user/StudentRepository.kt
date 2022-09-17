package com.info.info_v1_backend.domain.auth.data.repository.user

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository: JpaRepository<Student, Long> {

    fun findAllByStudentKeyStartingWith(studentKey: String): List<Student>
}