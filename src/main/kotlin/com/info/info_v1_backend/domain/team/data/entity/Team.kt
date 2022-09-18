package com.info.info_v1_backend.domain.team.data.entity

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.team.business.dto.request.EditTeamRequest
import com.info.info_v1_backend.global.base.entity.BaseAuthorEntity
import java.time.YearMonth
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Team(
    name: String,
    establishedAt: YearMonth,
    githubLink: String,
    header: Student
): BaseAuthorEntity() {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "team_name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "team_established_at")
    var establishedAt: YearMonth = establishedAt
        protected set


    @Column(name = "team_github_link", nullable = true)
    var githubLink: String? = githubLink
        protected set

    @OneToMany(cascade = [CascadeType.REMOVE])
    var affiliation: MutableList<Affiliation> = ArrayList()

    @ManyToOne(cascade = [CascadeType.REMOVE])
    var header: Student = header
        protected set

    fun editTeam(r: EditTeamRequest) {
        r.name?. let {
            this.name = it
        }
        r.establishedAt?. let{
            this.establishedAt = it
        }
        r.githubLink?. let{
            this.githubLink = it
        }
    }


}