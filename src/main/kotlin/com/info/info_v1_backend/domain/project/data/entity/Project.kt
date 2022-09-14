package com.info.info_v1_backend.domain.project.data.entity

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.team.data.Team
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

@Entity
@DiscriminatorColumn(name = "project_type")
sealed class Project(
    name: String,
    shortContent: String,
    developerList: MutableList<Student>,
    developTeam: Team,
    codeLinkList: MutableList<String>,
    tagList: MutableList<String>

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false)
    val id: Long? = null

    @Column(name = "project_name", nullable = false)
    var name: String = name

    @Column(name = "project_short_content", nullable = false)
    var shortContent: String = shortContent

//    @
//    var developerList: MutableList<Student> = developerList

    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "team_id")
    var developTeam: Team = developTeam

    @ElementCollection
    var codeLinkList: MutableList<String> = codeLinkList

    @ElementCollection
    var tagList: MutableList<String> = tagList

}