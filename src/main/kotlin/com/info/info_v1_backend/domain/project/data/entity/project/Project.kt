package com.info.info_v1_backend.domain.project.data.entity.project

import com.info.info_v1_backend.domain.auth.presentation.dto.response.ProjectList
import com.info.info_v1_backend.domain.project.data.entity.Creation
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import com.info.info_v1_backend.domain.team.data.entity.Team
import com.info.info_v1_backend.global.base.entity.BaseAuthorEntity
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.DiscriminatorColumn
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
@DiscriminatorColumn(name = "project_type")
sealed class Project(
    name: String,
    shortContent: String,
    creationList: MutableList<Creation>,
    developTeam: Team,
    codeLinkList: MutableList<String>,
    tagList: MutableList<String>,
    projectStatus: ProjectStatus
): BaseAuthorEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false)
    val id: Long? = null

    @Column(name = "project_name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "project_short_content", nullable = false)
    var shortContent: String = shortContent
        protected set

    @OneToMany(mappedBy = "project")
    var creationList: MutableList<Creation> = creationList
        protected set

    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "team_id")
    var developTeam: Team = developTeam
        protected set

    @ElementCollection
    var codeLinkList: MutableList<String> = codeLinkList
        protected set

    @ElementCollection
    var tagList: MutableList<String> = tagList
        protected set

    @Column(name = "project_status")
    var status: ProjectStatus = projectStatus
        protected set

    fun toProjectList(): ProjectList{
        return ProjectList(
                this.name,
                this.id.toString(),
                this.status,
        )
    }

}