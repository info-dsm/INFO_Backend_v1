package com.info.info_v1_backend.infra.amazon.s3.env

import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary


@Configuration
class S3Config {

    @Bean
    @Primary
    fun s3Client(): AmazonS3 {
        return AmazonS3ClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_2).build()
    }

}