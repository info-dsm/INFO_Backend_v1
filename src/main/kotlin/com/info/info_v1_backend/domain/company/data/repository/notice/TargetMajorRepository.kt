package com.info.info_v1_backend.domain.company.data.repository.notice

import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.notice.embeddable.TargetMajor
import com.info.info_v1_backend.domain.company.data.entity.type.MajorType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface TargetMajorRepository: JpaRepository<TargetMajor, Long> {

    fun findFirstByNoticeAndMajorOrderBycreatedAtDesc(notice: Notice, major: MajorType): Optional<TargetMajor>
}