package com.info.info_v1_backend.domain.team.data.entity

import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import javax.persistence.*

@Entity
class Affiliation(
    user: User,
    team: Team
): BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne
    @JoinColumn(name = "affiliation_user", nullable = false)
    var user: User = user
        protected set
    @ManyToOne
    @JoinColumn(name = "affiliation_team", nullable = false)
    var team: Team = team
        protected set

}