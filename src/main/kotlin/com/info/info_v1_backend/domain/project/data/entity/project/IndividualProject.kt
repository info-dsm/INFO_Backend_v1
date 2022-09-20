package com.info.info_v1_backend.domain.project.data.entity.project

import com.info.info_v1_backend.domain.project.business.dto.request.EditIndividualProjectDto
import com.info.info_v1_backend.domain.project.data.entity.Creation
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("individual")
class IndividualProject(
    name: String,
    shortContent: String,
    creationList: MutableList<Creation>,
    codeLinkList: MutableList<String>?,
    tagList: MutableList<String>
): Project(
    name,
    shortContent,
    creationList,
    codeLinkList,
    tagList,
    ProjectStatus.APPROVE
) {
    fun editIndividualProject(request: EditIndividualProjectDto){
        request.id?. let {
            this.id = it
        }
        request.photoList?.let {
            this.photoList = it
        }
        request.name?. let {
            this.name = it
        }
        request.shortContent?. let {
            this.shortContent = it
        }
        request.haveSeenCount?. let {
            this.haveSeenCount = it
        }
        request.creationList?. let{
            this.creationList = it.toMutableList()
        }
        request.codeLinkList?. let {
            this.codeLinkList = it
        }
        request.tagList?. let {
            this.tagList = it
        }
        request.status?.let {
            this.status = it
        }
    }
}