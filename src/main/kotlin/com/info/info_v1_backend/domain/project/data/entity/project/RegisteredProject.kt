 package com.info.info_v1_backend.domain.project.data.entity.project

import com.info.info_v1_backend.domain.project.business.dto.request.EditRegisteredProjectDto
import com.info.info_v1_backend.domain.project.data.entity.Creation
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import javax.persistence.*


@Entity
@DiscriminatorValue(value = "registered")
@Inheritance(strategy = InheritanceType.JOINED)
class RegisteredProject(
    name: String,
    shortContent: String,
    purpose: String,
    theoreticalBackground: MutableList<String>,
    processList: MutableList<String>,
    result: String,
    conclusion: String,
    referenceList: MutableList<String>,
    creationList: MutableList<Creation>?,
    codeLinkList: MutableList<String>,
    tagList: MutableList<String>
): Project(
    name,
    shortContent,
    creationList,
    codeLinkList,
    tagList,
    ProjectStatus.WAITING
) {

    var purpose: String = purpose
        protected set

    @ElementCollection
    var theoreticalBackground: MutableList<String> = theoreticalBackground
        protected set

    @ElementCollection
    var processList:  MutableList<String> = processList
        protected set

    @Column(name = "registered_project_result")
    var result: String = result
        protected set

    @Column(name = "registered_project_conclusion", nullable = false)
    var conclusion: String = conclusion
        protected set

    @ElementCollection
    var referenceList: MutableList<String> = referenceList

    fun editRegisteredProject(request: EditRegisteredProjectDto){
        request.id?. let {
            this.id = it
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
        request.status?. let {
            this.status = it
        }
        request.purpose?. let {
            this.purpose = it
        }
        request.theoreticalBackground?. let {
            this.theoreticalBackground = it
        }
        request.processList?. let {
            this.processList = it
        }
        request.result?. let {
            this.result = it
        }
        request.conclusion?. let {
            this.result = it
        }
        request.referenceList?. let {
            this.referenceList = it
        }
        request.creationList?. let {
            this.creationList = it
        }
        request.codeLinkList?. let {
            this.codeLinkList = it
        }
        request.tagList?. let {
            this.tagList = it
        }
    }

}