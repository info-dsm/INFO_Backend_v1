package com.info.info_v1_backend.global.database.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("datasource")
@ConstructorBinding
data class DataSourceListProperty(
    val mysql: RDBSourceProperty,
    val mongo: NosqlSourceProperty

)

data class RDBSourceProperty(
    val driverClassName: String,
    val url: String,
    val userName: String,
    val password: String
)

data class NosqlSourceProperty(
    val host: String,
    val port: Int,
    val authenticationDatabase: String,
    val username: String,
    val password: String,
    val database: String
)