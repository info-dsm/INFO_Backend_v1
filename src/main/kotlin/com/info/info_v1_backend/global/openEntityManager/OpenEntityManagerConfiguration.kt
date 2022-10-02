package com.info.info_v1_backend.global.openEntityManager

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter


@Configuration
class OpenEntityManagerConfiguration {
    @Bean
    fun openEntityManagerInViewFilter(): FilterRegistrationBean<OpenEntityManagerInViewFilter> {
        val filterFilterRegistrationBean = FilterRegistrationBean<OpenEntityManagerInViewFilter>()
        filterFilterRegistrationBean.filter = OpenEntityManagerInViewFilter()
        filterFilterRegistrationBean.order = Int.MIN_VALUE
        return filterFilterRegistrationBean
    }
}
