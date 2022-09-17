package com.info.info_v1_backend.global.batch.scheduler

import com.info.info_v1_backend.global.batch.config.JobConfiguration
import org.slf4j.LoggerFactory
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException
import org.springframework.batch.core.repository.JobRestartException
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
class JobScheduler(
//    private val jobLauncher: JobLauncher,
//    private val jobConfiguration: JobConfiguration,
//    private val jobExplorer: JobExplorer
) {
//
//    private val log = LoggerFactory.getLogger(this.javaClass)
//
//    init {
//        val jobParameters = JobParameters()
//        try {
//            jobLauncher.run(jobConfiguration.studentEmploymentJob(),
//                JobParametersBuilder(jobExplorer)
//                .getNextJobParameters(jobConfiguration.studentEmploymentJob())
//                    .toJobParameters()
//            )
//        } catch (e: JobExecutionAlreadyRunningException) {
//            log.error(e.message)
//        } catch (e: JobInstanceAlreadyCompleteException) {
//            log.error(e.message)
//        } catch (e: JobParametersInvalidException) {
//            log.error(e.message)
//        } catch (e: JobRestartException) {
//            log.error(e.message)
//        }
//    }
//
//    @Scheduled(cron = "0 12 * * *")
//    fun runJob() {
//        val confMap: MutableMap<String, JobParameter> = HashMap()
//        confMap["time"] = JobParameter(System.currentTimeMillis())
//        val jobParameters = JobParameters(confMap)
//        try {
//            jobLauncher.run(jobConfiguration.studentEmploymentJob(), jobParameters)
//        } catch (e: JobExecutionAlreadyRunningException) {
//            log.error(e.message)
//        } catch (e: JobInstanceAlreadyCompleteException) {
//            log.error(e.message)
//        } catch (e: JobParametersInvalidException) {
//            log.error(e.message)
//        } catch (e: JobRestartException) {
//            log.error(e.message)
//        }
//    }

}