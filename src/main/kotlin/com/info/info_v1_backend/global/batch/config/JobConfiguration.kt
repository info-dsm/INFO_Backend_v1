package com.info.info_v1_backend.global.batch.config

import org.springframework.context.annotation.Configuration


@Configuration
class JobConfiguration(
//    private val jobBuilderFactory: JobBuilderFactory,
//    private val stepBuilderFactory: StepBuilderFactory,
//    private val dbSource: DatabaseConfig,
//    @Qualifier(DatabaseConfig.MONGO_JDBC_TEMPLATE)
//    private val mongoJdbcTemplate: JdbcTemplate,
//    private val entityManagerFactory: EntityManagerFactory
) {
//    private val log = LoggerFactory.getLogger(javaClass)
//
//    companion object {
//        const val CHUNK_SIZE: Int = 10
//        const val STUDENT_EMPLOYMENT_JOB_NAME = "studentEmploymentJobName"
//        const val STUDENT_EMPLOYMENT_STEP_NAME = "studentEmploymentStepName"
//    }
//
//    @Bean
//    fun studentEmploymentJob(): Job {
//        return jobBuilderFactory.get(STUDENT_EMPLOYMENT_JOB_NAME)
//            .incrementer(RunIdIncrementer())
//            .start(studentEmploymentStep())
//            .build()
//    }
//
//    @Bean
//    fun studentEmploymentStep(): Step {
//        return stepBuilderFactory[STUDENT_EMPLOYMENT_STEP_NAME]
//            .chunk<Student, Indication>(CHUNK_SIZE)
//            .reader(studentEmploymentReader())
//            .processor(studentEmploymentProcessor())
//            .writer(studentEmploymentWriter())
//            .build()
//    }
//
//    @Bean
//    fun studentEmploymentReader(): JpaPagingItemReader<Student> {
//        return JpaPagingItemReaderBuilder<Student>()
//            .name("studentEmploymentReader")
//            .entityManagerFactory(entityManagerFactory)
//            .pageSize(CHUNK_SIZE)
//            .queryString("SELECT s FROM Student s")
//            .build()
//    }
//
//    @Bean
//    fun studentEmploymentProcessor(): ItemProcessor<Student, Indication> {
//        return ItemProcessor<Student, Indication> {
//            item: Student ->
//            val indication: Indication? = item.company?.let {
//                Indication(
//                    it.id!!,
//                    it.shortName,
//                    it.photoList[0]
//                )
//            }
//            indication?.let {
//                log.info("New updated: $it")
//            }
//            return@ItemProcessor indication
//        }
//    }
//
//    @Bean
//    fun studentEmploymentWriter(): JpaItemWriter<Indication> {
//        val jpaItemWriter: JpaItemWriter<Indication> = JpaItemWriter<Indication>()
//        jpaItemWriter.setEntityManagerFactory(entityManagerFactory)
//        return jpaItemWriter
//
//    }


}
