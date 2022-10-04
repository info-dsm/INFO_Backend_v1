package com.info.info_v1_backend.domain.company.data.repository.company

import com.info.info_v1_backend.domain.company.data.entity.notice.language.Language
import org.springframework.data.jpa.repository.JpaRepository

interface LanguageRepository: JpaRepository<Language, String> {
}