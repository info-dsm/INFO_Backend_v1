package com.info.info_v1_backend

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(
//    excludeName = ["org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"],
//    exclude = [JmxAutoConfiguration::class],
)
@EnableScheduling
@ConfigurationPropertiesScan
class InfoV1BackendApplication

fun main(args: Array<String>) {
    runApplication<InfoV1BackendApplication>(*args)
}
