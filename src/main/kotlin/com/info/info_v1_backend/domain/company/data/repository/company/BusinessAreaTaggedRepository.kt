package com.info.info_v1_backend.domain.company.data.repository.company

import com.info.info_v1_backend.domain.company.data.entity.company.tag.BusinessAreaTagged
import com.info.info_v1_backend.domain.company.data.entity.company.tag.BusinessAreaTaggedIdClass
import org.springframework.data.jpa.repository.JpaRepository

interface BusinessAreaTaggedRepository: JpaRepository<BusinessAreaTagged, BusinessAreaTaggedIdClass> {

}