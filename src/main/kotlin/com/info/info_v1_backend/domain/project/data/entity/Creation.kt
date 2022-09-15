package com.info.info_v1_backend.domain.project.data.entity

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Creation(
    project: Project,
    student: Student
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null


    @ManyToOne
    @JoinColumn(name = "project_id")
    var project: Project = project
        protected set

    @ManyToOne
    @JoinColumn(name = "user_id")
    var student: Student = student
        protected set



}