package com.info.info_v1_backend.global.batch.config

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.board.data.entity.Indication
import com.info.info_v1_backend.global.database.DatabaseConfig
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import javax.persistence.EntityManagerFactory


@Configuration
class JobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val dbSource: DatabaseConfig,
    @Qualifier(DatabaseConfig.MONGO_JDBC_TEMPLATE)
    private val mongoJdbcTemplate: JdbcTemplate,
    private val entityManagerFactory: EntityManagerFactory
) {
    private val log = LoggerFactory.getLogger(javaClass)

    companion object {
        const val CHUNK_SIZE: Int = 10
        const val STUDENT_EMPLOYMENT_JOB_NAME = "studentEmploymentJobName"
        const val STUDENT_EMPLOYMENT_STEP_NAME = "studentEmploymentStepName"
    }

    @Bean
    fun studentEmploymentJob(): Job {
        return jobBuilderFactory.get(STUDENT_EMPLOYMENT_JOB_NAME)
            .incrementer(RunIdIncrementer())
            .start(studentEmploymentStep())
            .build()
    }

    @Bean
    fun studentEmploymentStep(): Step {
        return stepBuilderFactory[STUDENT_EMPLOYMENT_STEP_NAME]
            .chunk<Student, Indication>(CHUNK_SIZE)
            .reader(studentEmploymentReader())
            .processor(studentEmploymentProcessor())
            .writer(studentEmploymentWriter())
            .build()
    }

    @Bean
    fun studentEmploymentReader(): JpaPagingItemReader<Student> {
        return JpaPagingItemReaderBuilder<Student>()
            .name("studentEmploymentReader")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(CHUNK_SIZE)
            .queryString("SELECT s FROM Student s")
            .build()
    }

    @Bean
    fun studentEmploymentProcessor(): ItemProcessor<Student, Indication> {
        return ItemProcessor<Student, Indication> {
            item: Student ->
            val indication: Indication? = item.company?.let {
                Indication(
                    it.id!!,
                    it.shortName,
                    it.photoList[0]
                )
            }
            indication?.let {
                log.info("New updated: $it")
            }
            return@ItemProcessor indication
        }
    }

    @Bean
    fun studentEmploymentWriter(): JpaItemWriter<Indication> {
        val jpaItemWriter: JpaItemWriter<Indication> = JpaItemWriter<Indication>()
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory)
        return jpaItemWriter

    }


}
