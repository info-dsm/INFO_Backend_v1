package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.project.business.dto.request.SortCriteriaType
import com.info.info_v1_backend.domain.project.business.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.dto.response.MinimumProjectListResponse
import com.info.info_v1_backend.domain.project.business.dto.response.MinimumProjectResponse
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import com.info.info_v1_backend.domain.project.data.repository.RegisteredProjectRepository
import com.info.info_v1_backend.domain.project.exception.ProjectNotFoundException
import com.info.info_v1_backend.domain.project.exception.ProjectStatusWaitingException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class RegisteredProjectServiceImpl(
    private val registeredProjectRepository: RegisteredProjectRepository
): RegisteredProjectService {

    override fun getApprovedMinimumProjectList(idx: Int, size: Int, sortType: SortCriteriaType): Page<MinimumProjectResponse> {
        //security config에서 url로 authentication필요
        return registeredProjectRepository.findAllByStatus(
            ProjectStatus.APPROVE,
            PageRequest.of(idx, size, Sort.by(sortType.criteria).descending())
        ).map {
            MinimumProjectResponse(
                it.id!!,
                it.name,
                it.createdAt,
                it.updatedAt,
                it.createdBy,
                it.updatedBy,
                it.shortContent,
                it.haveSeenCount,
                it.codeLinkList,
            )
        }
    }

    override fun getMaximumProject(id: Long): MaximumProjectResponse {
        //security config에서 url로 authentication필요
        val p = registeredProjectRepository.findByIdOrNull(id)
            ?: throw ProjectNotFoundException("$id :: not found")
        if (p.status == ProjectStatus.WAITING) {
            throw ProjectStatusWaitingException("$id :: status waiting")
        }
        return MaximumProjectResponse(
            p.photoList.map {
                it.toImageDto()
            },
            p.name,
            p.createdAt,
            p.updatedAt,
            p.createdBy,
            p.updatedBy,
            p.status,
            p.shortContent,
            p.haveSeenCount,
            p.codeLinkList
        )
    }

    override fun writeProject() {
        TODO("Not yet implemented")
    }

    override fun approveProject() {
        TODO("Not yet implemented")
    }

    override fun rejectProject() {
        TODO("Not yet implemented")
    }

    override fun getWaitingMinimumProjectList(): List<MinimumProjectResponse> {
        TODO("Not yet implemented")
    }

}