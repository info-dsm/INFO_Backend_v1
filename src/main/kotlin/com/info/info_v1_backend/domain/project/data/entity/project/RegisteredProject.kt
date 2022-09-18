package com.info.info_v1_backend.domain.project.data.entity.project

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
    creationList: MutableList<Creation>,
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


}