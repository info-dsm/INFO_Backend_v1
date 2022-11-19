package com.info.info_v1_backend.global.auditing

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*


@Configuration
@EnableJpaAuditing
class AuditorAwareConfiguration: AuditorAware<Long> {

    override fun getCurrentAuditor(): Optional<Long> {
        SecurityContextHolder.getContext().authentication?.credentials?.let {
            if (it == "") return Optional.empty()
            return Optional.of(it.toString().toLong())
        }?: return Optional.empty()
    }

}