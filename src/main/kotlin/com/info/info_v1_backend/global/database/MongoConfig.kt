package com.info.info_v1_backend.global.database

import com.info.info_v1_backend.global.database.env.DataSourceProperty
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import java.util.*


@Configuration
class MongoConfig(
    private val datasourceProperty: DataSourceProperty
) : AbstractMongoClientConfiguration() {

    override fun getDatabaseName(): String {
        return "info-v1"
    }

    override fun mongoClient(): MongoClient {
        val connectionString = ConnectionString(datasourceProperty.mongo.url)
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()
        return MongoClients.create(mongoClientSettings)
    }


}