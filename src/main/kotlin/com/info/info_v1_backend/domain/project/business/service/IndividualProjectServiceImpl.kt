package com.info.info_v1_backend.domain.project.business.service

import com.info.info_v1_backend.domain.auth.data.repository.user.StudentRepository
import com.info.info_v1_backend.domain.project.business.dto.request.IndividualProjectRequest
import com.info.info_v1_backend.domain.project.business.dto.response.MaximumProjectResponse
import com.info.info_v1_backend.domain.project.business.dto.response.MinimumProjectListResponse
import com.info.info_v1_backend.domain.project.business.dto.response.MinimumProjectResponse
import com.info.info_v1_backend.domain.project.data.entity.Creation
import com.info.info_v1_backend.domain.project.data.entity.project.IndividualProject
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import com.info.info_v1_backend.domain.project.data.repository.CreationRepository
import com.info.info_v1_backend.domain.project.exception.NotHaveAccessProjectException
import com.info.info_v1_backend.domain.project.exception.ProjectNotFoundException
import com.info.info_v1_backend.global.util.user.CurrentUtil
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class IndividualProjectServiceImpl(
    private val individualRepository: ProjectRepository<IndividualProject>,
    private val currentUtil: CurrentUtil,
    private val userRepository: StudentRepository,
    private val creationRepository: CreationRepository
    ): IndividualProjectService{

    override fun getMinimumLatestOrderProjectList   (): MinimumProjectListResponse {
        //security config에서 url로 authentication필요
        return MinimumProjectListResponse(individualRepository.findAll(
            Sort.by("createdAt").descending())
            .stream()
            .filter { it.status == ProjectStatus.APPROVE }
            .map{
                MinimumProjectResponse(
                    it.name,
                    it.createdAt,
                    it.updatedAt,
                    it.createdBy,
                    it.updatedBy,
                    it.shortContent,
                    it.haveSeenCount,
                    it.codeLinkList,
                )
            }.collect(Collectors.toList()))
    }

    override fun getMinimumNumberOfViewsProjectList(): MinimumProjectListResponse {
        //security config에서 url로 authentication필요
        return MinimumProjectListResponse(individualRepository.findAll(
            Sort.by(Sort.Direction.DESC, "haveSeenCount"))
            .stream()
            .filter { it.status == ProjectStatus.APPROVE }
            .map{
                MinimumProjectResponse(
                    it.name,
                    it.createdAt,
                    it.updatedAt,
                    it.createdBy,
                    it.updatedBy,
                    it.shortContent,
                    it.haveSeenCount,
                    it.codeLinkList,
                )
            }.collect(Collectors.toList()))
    }

    override fun getMaximumProject(id: Long): MaximumProjectResponse {
        //security config에서 url로 authentication필요
        val p = individualRepository.findByIdOrNull(id)
            ?: throw ProjectNotFoundException("$id :: not found")
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

    override fun writeIndividualProject(request: IndividualProjectRequest) {
        val current = currentUtil.getCurrentUser()
        if(request.creationList.any { it.student == current }){
            val creations = request.creationList.stream()
                .map { 
                    creationRepository.save(
                        Creation(
                            it.project,
                            it.student
                        )
                    )
                }.collect(Collectors.toList())
            individualRepository.save(
                IndividualProject(
                    request.name,
                    request.shortContent,
                    creations,
                    request.githubLinkList,
                    request.tagList
                )
            )
        }
        throw NotHaveAccessProjectException("작성자가 프로젝트에 참여하지 않음")
    }

}