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
        const val READ_DATASOURCE = "readDatasource"
        const val WRITE_DATASOURCE = "writeDatasource"
        const val WRITE_JDBC_TEMPLATE = "writeJdbcTemplate"
    }


    @Bean(name = [READ_DATASOURCE])
    fun readDatasource(): DataSource {
        return getDatasource(prop.read.driverClassName, prop.read.url, prop.read.userName, prop.read.password)
    }

    @PreDestroy
    fun destroyReadDatasource() {
        destroyDatasource(readDatasource())
    }

    @Bean(name = [WRITE_DATASOURCE])
    fun writeDatasource(): DataSource {
        return getDatasource(prop.write.driverClassName, prop.write.url, prop.write.userName, prop.write.password)
    }


    @PreDestroy
    fun destroyWriteDatasource() {
        destroyDatasource(writeDatasource())
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


    private fun getDatasource(driverClassName: String, url: String, username: String, password: String): DataSource {
        return DataSourceBuilder.create()
            .driverClassName(driverClassName)
            .url(url)
            .username(username)
            .password(password)
            .build()
    }

    @Bean(name = [WRITE_JDBC_TEMPLATE])
    @Primary
    fun jdbcTemplate(): JdbcTemplate {
        val template = JdbcTemplate(writeDatasource())
        return template
    }

    private fun destroyDatasource(datasource: DataSource) {
        datasource.connection.close()
    }

}