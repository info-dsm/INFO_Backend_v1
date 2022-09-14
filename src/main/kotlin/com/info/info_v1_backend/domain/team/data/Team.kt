package com.info.info_v1_backend.domain.team.data

import com.info.info_v1_backend.global.base.entity.BaseAuthorEntity
import java.time.LocalDate
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Team(
    name: String,
    establishedAt: LocalDate,
    githubLink: String
): BaseAuthorEntity() {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "team_name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "team_established_at")
    val establishedAt: LocalDate = establishedAt

    @Column(name = "team_github_link", nullable = true)
    var githubLink: String? = githubLink

    @OneToMany(cascade = [CascadeType.REMOVE])
    var affiliation: MutableList<Affiliation> = ArrayList()

    //header 있어야함


}