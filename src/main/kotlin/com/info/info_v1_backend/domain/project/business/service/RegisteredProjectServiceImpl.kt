package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.project.business.controller.dto.ProjectListResponse
import com.info.info_v1_backend.domain.project.business.controller.dto.ProjectResponse
import com.info.info_v1_backend.domain.project.data.entity.project.RegisteredProject
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import com.info.info_v1_backend.domain.project.data.repository.ProjectRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class RegisteredProjectServiceImpl(
    private val registeredProjectRepository: ProjectRepository<RegisteredProject>
    ): RegisteredProjectService {
    override fun getMinimumLatestOrderProjectList(): ProjectListResponse { //security config에서 url로 authentication필요
        return ProjectListResponse(registeredProjectRepository.findAll(Sort
            .by(Sort.Direction.DESC, "createdBy"))
            .stream()
            .filter { it.status == ProjectStatus.APPROVE }
            .map{ProjectResponse(
                name = it.name,
                haveSeenCount = it.haveSeenCount,
                createAt = it.createdDate,
                updateAt = it.updateDate,
                createdBy = it.createdBy,
                updatedBy = it.updatedBy,
                shortContent = it.shortContent
            )}.collect(Collectors.toList()))
    }
    override fun getMinimumNumberOfViewsProjectList(): ProjectListResponse { //security config에서 url로 authentication필요
        return ProjectListResponse(registeredProjectRepository.findAll(Sort
            .by(Sort.Direction.DESC, "haveSeenCount"))
            .stream()
            .filter { it.status == ProjectStatus.APPROVE }
            .map{ProjectResponse(
                name = it.name,
                haveSeenCount = it.haveSeenCount,
                createAt = it.createdDate,
                updateAt = it.updateDate,
                createdBy = it.createdBy,
                updatedBy = it.updatedBy,
                shortContent = it.shortContent
            )}.collect(Collectors.toList()))
    }
}