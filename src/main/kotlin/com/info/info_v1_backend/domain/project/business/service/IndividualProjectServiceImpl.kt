package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.project.business.controller.dto.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.controller.dto.MinimumProjectListResponse
import com.info.info_v1_backend.domain.project.business.controller.dto.MinimumProjectResponse
import com.info.info_v1_backend.domain.project.data.entity.project.IndividualProject
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import com.info.info_v1_backend.domain.project.data.repository.ProjectRepository
import com.info.info_v1_backend.domain.project.exception.ProjectNotFoundException
import com.info.info_v1_backend.domain.project.exception.ProjectStatusWaitingException
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class IndividualProjectServiceImpl(
    private val individualRepository: ProjectRepository<IndividualProject>
    ): IndividualProjectService{

    override fun getMinimumLatestOrderProjectList   (): MinimumProjectListResponse {
        //security config에서 url로 authentication필요
        return MinimumProjectListResponse(individualRepository.findAll(
            Sort.by(Sort.Direction.DESC, "createdBy"))
            .stream()
            .filter { it.status == ProjectStatus.INDIVIDUAL }
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
            )}.collect(Collectors.toList()))
    }

    override fun getMinimumNumberOfViewsProjectList(): MinimumProjectListResponse {
        //security config에서 url로 authentication필요
        return MinimumProjectListResponse(individualRepository.findAll(
            Sort.by(Sort.Direction.DESC, "haveSeenCount"))
            .stream()
            .filter { it.status == ProjectStatus.INDIVIDUAL }
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
                )}.collect(Collectors.toList()))
    }

    override fun getMaximumProject(id: Long): MaximumProjectResponse {
        //security config에서 url로 authentication필요
        val p = individualRepository.findByIdOrNull(id)
            ?: throw ProjectNotFoundException("$id :: not found")
        return MaximumProjectResponse(
            name = p.name,
            imageLink = p.imageLinkList,
            createBy = p.createdBy,
            updateBy = p.updatedBy,
            createAt = p.createdDate,
            updateAt = p.updateDate,
            projectStatus = ProjectStatus.INDIVIDUAL,
            shortContent = p.shortContent,
            haveSeenCount = p.haveSeenCount,
            githubLinkList = p.codeLinkList
        )
    }

}