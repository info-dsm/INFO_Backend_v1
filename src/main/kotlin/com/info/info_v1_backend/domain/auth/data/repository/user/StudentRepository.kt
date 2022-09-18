package com.info.info_v1_backend.domain.auth.data.repository.user

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface StudentRepository: JpaRepository<Student, Long> {

    fun findAllByStudentKeyStartingWith(studentKey: String): List<Student>
    fun findAllByStartingWith(): List<Student>
    fun findByName(name: String) : Optional<Student>
}