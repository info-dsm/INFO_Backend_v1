package com.info.info_v1_backend.domain.company.data.entity.notice.certificate

import com.info.info_v1_backend.global.base.entity.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id


@Entity
class Certificate(
    name: String
): BaseTimeEntity() {

    @Id
    @Column(name = "certificate_name")
    val name: String = name


}