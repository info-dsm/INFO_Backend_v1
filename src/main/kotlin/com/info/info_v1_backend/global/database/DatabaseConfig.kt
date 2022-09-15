package com.info.info_v1_backend.global.database

import com.info.info_v1_backend.global.database.env.DataSourceListProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.env.Environment
import org.springframework.jdbc.core.JdbcTemplate
import javax.annotation.PreDestroy
import javax.sql.DataSource


@Configuration
class DatabaseConfig(
    private val prop: DataSourceListProperty,
    private val env: Environment
) {
    companion object {
        const val MONGO_DB = "mongoDb"
        const val MONGO_JDBC_TEMPLATE = "mongoJdbcTemplate"
    }


    @Bean(name = [MONGO_DB])
    fun getMongoDBSource(): DataSource {
        return DataSourceBuilder.create()
            .url(prop.mongo.url)
            .build()
    }

    @PreDestroy
    fun destroyMongoDbSource() {
        destroyDatasource(getMongoDBSource())
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    fun defaultDatasource(): DataSource {
        return DataSourceBuilder.create()
            .driverClassName(env.getProperty("spring.datasource.driver-class-name"))
            .url(env.getProperty("spring.datasource.url"))
            .build()
    }

    @PreDestroy
    fun destroyDefaultDatasource() {
        destroyDatasource(defaultDatasource())
    }

    @Bean(name = [MONGO_JDBC_TEMPLATE])
    @Primary
    fun jdbcTemplate(): JdbcTemplate {
        val template = JdbcTemplate(getMongoDBSource())
        return template
    }

    private fun destroyDatasource(datasource: DataSource) {
        datasource.connection.close()
    }

}