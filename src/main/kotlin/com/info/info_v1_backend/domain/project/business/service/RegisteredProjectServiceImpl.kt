package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.project.business.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.dto.response.MinimumProjectListResponse
import com.info.info_v1_backend.domain.project.business.dto.response.MinimumProjectResponse
import com.info.info_v1_backend.domain.project.data.entity.project.RegisteredProject
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import com.info.info_v1_backend.domain.project.data.repository.ProjectRepository
import com.info.info_v1_backend.domain.project.exception.ProjectNotFoundException
import com.info.info_v1_backend.domain.project.exception.ProjectStatusWaitingException
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class RegisteredProjectServiceImpl(
    private val registeredProjectRepository: ProjectRepository<RegisteredProject>
    ): RegisteredProjectService {

    override fun getMinimumLatestOrderProjectList(): MinimumProjectListResponse {
        //security config에서 url로 authentication필요
        return MinimumProjectListResponse(registeredProjectRepository.findAll(Sort
            .by(Sort.Direction.DESC, "createdBy"))
            .stream()
            .filter { it.status == ProjectStatus.APPROVE }
            .map{
                MinimumProjectResponse(
                name = it.name,
                haveSeenCount = it.haveSeenCount,
                createAt = it.createdDate,
                updateAt = it.updateDate,
                createdBy = it.createdBy,
                updatedBy = it.updatedBy,
                shortContent = it.shortContent,
                githubLinkList = it.codeLinkList
            )
            }.collect(Collectors.toList()))
    }

    override fun getMinimumNumberOfViewsProjectList(): MinimumProjectListResponse {
        //security config에서 url로 authentication필요
        return MinimumProjectListResponse(registeredProjectRepository.findAll(Sort
            .by(Sort.Direction.DESC, "haveSeenCount"))
            .stream()
            .filter { it.status != ProjectStatus.APPROVE }
            .map{
                MinimumProjectResponse(
                name = it.name,
                haveSeenCount = it.haveSeenCount,
                createAt = it.createdDate,
                updateAt = it.updateDate,
                createdBy = it.createdBy,
                updatedBy = it.updatedBy,
                shortContent = it.shortContent,
                githubLinkList = it.codeLinkList
            )
            }.collect(Collectors.toList()))
    }

    override fun getMaximumProject(id: Long): MaximumProjectResponse {
        //security config에서 url로 authentication필요
        val p = registeredProjectRepository.findByIdOrNull(id)
            ?: throw ProjectNotFoundException("$id :: not found")
        if (p.status == ProjectStatus.WAITING) {
            throw ProjectStatusWaitingException("$id :: status waiting")
        }
        return MaximumProjectResponse(
            name = p.name,
            imageLink = p.imageLinkList,
            createBy = p.createdBy,
            updateBy = p.updatedBy,
            createAt = p.createdDate,
            updateAt = p.updateDate,
            projectStatus = p.status,
            shortContent = p.shortContent,
            haveSeenCount = p.haveSeenCount,
            githubLinkList = p.codeLinkList
        )
    }

}