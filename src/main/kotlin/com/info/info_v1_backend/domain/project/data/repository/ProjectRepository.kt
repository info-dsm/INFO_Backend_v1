package com.info.info_v1_backend.domain.project.data.repository
import com.info.info_v1_backend.domain.project.data.entity.project.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository<T:Project >: JpaRepository<T, Long> {
}