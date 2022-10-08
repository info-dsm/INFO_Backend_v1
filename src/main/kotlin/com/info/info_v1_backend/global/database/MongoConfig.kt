package com.info.info_v1_backend.global.database

import com.info.info_v1_backend.domain.company.data.entity.company.CompanySearchDocument
import com.info.info_v1_backend.global.database.env.DataSourceProperty
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.index.IndexDefinition
import org.springframework.data.mongodb.core.index.IndexResolver
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean
import javax.annotation.PostConstruct


@Configuration

class MongoConfig(
    private val datasourceProperty: DataSourceProperty,
//    private val operations: MongoOperations
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

    override fun autoIndexCreation(): Boolean {
        return true
    }


//    @PostConstruct
//    fun postConstruct() {
//        val resolver: IndexResolver = IndexResolver.create(operations.converter.mappingContext)
//        resolver.resolveIndexFor(CompanySearchDocument::class.java).forEach {
//                indexDefinition: IndexDefinition ->
//            operations.indexOps(
//                CompanySearchDocument::class.java
//            ).ensureIndex(indexDefinition)
//        }
//    }

}