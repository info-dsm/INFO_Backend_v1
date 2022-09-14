package com.info.info_v1_backend.global.base.entity

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseAuthorEntity():BaseTimeEntity() {

    @CreatedBy
    open var createdBy: Long? = null
        protected set

    @LastModifiedBy
    open var updatedBy: Long? = null
        protected set


}
