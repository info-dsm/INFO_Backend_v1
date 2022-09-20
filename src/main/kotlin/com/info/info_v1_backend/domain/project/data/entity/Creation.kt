package com.info.info_v1_backend.domain.project.data.entity

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.project.data.entity.project.Project
import javax.persistence.*

@Entity
class Creation(
    project: Project?,
    student: Student
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null


    @ManyToOne
    @JoinColumn(name = "project_id")
    var project: Project? = project
        protected set

    @ManyToOne
    @JoinColumn(name = "user_id")
    var student: Student = student
        protected set

    fun editCreation(project: Project?){
        project?. let{
            this.project = project
        }
    }
}