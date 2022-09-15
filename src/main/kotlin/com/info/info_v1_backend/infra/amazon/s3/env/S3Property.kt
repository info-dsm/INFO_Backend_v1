package com.info.info_v1_backend.infra.amazon.s3.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding


@ConfigurationProperties("cloud.aws.s3")
@ConstructorBinding
data class S3Property (
    val bucketName: String
)
