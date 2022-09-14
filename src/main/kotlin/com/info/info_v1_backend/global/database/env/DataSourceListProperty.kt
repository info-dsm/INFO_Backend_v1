package com.info.info_v1_backend.global.database.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("datasource")
@ConstructorBinding
data class DataSourceListProperty(
    val read: DataSourceProperty,
    val write: DataSourceProperty

)

data class DataSourceProperty(
    val driverClassName: String,
    val url: String,
    val userName: String,
    val password: String
)

