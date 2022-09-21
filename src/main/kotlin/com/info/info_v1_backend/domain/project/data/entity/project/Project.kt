package com.info.info_v1_backend.domain.project.data.entity.project

import com.info.info_v1_backend.domain.auth.presentation.dto.response.ProjectList
import com.info.info_v1_backend.domain.project.data.entity.Creation
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import com.info.info_v1_backend.global.base.entity.BaseAuthorEntity
import com.info.info_v1_backend.global.file.entity.File
import javax.persistence.*

@Entity
@DiscriminatorColumn(name = "project_type")
sealed class Project(
    name: String,
    shortContent: String,
    creationList: MutableList<Creation>,
    codeLinkList: MutableList<String>?,
    tagList: MutableList<String>,
    projectStatus: ProjectStatus
): BaseAuthorEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false)
    var id: Long? = null

    @Column(name = "project_name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "project_short_content", nullable = false)
    var shortContent: String = shortContent
        protected set

    @Column(name = "have_seen_count", nullable = false)
    var haveSeenCount: Long = 0
        protected set

    @OneToMany(mappedBy = "project")
    var creationList: MutableList<Creation>? = creationList
        protected set

    @ElementCollection
    var codeLinkList: MutableList<String>? = codeLinkList
        protected set

    @ElementCollection
    var tagList: MutableList<String> = tagList
        protected set

    @Column(name = "project_status")
    var status: ProjectStatus = projectStatus
        protected set

    @OneToMany(mappedBy = "project", cascade = [CascadeType.REMOVE])
    var photoList: MutableList<File>? = ArrayList()
        protected set

    fun toProjectList(): ProjectList{
        return ProjectList(
                this.name,
                this.codeLinkList?.map {
                    it
                }?.toList(),
                this.status,
        )
    }

}