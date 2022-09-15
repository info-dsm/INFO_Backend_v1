package com.info.info_v1_backend.global.database.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("datasource")
@ConstructorBinding
data class DataSourceListProperty(
    val mongo: NosqlSourceProperty

)

data class NosqlSourceProperty(
    val url: String
)