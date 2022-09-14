package com.info.info_v1_backend.global.batch.service

import com.info.info_v1_backend.global.batch.config.JobConfiguration
import com.info.info_v1_backend.global.batch.data.entity.BatchPost
import com.info.info_v1_backend.global.batch.data.entity.BatchWritePost
import com.info.info_v1_backend.global.database.DatabaseConfiguration
import com.info.info_v1_backend.global.komoran.service.KomoranService
import com.info.info_v1_backend.infra.alarm.AlarmService
import com.info.info_v1_backend.infra.ncloud.clova.ClovaService
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class BatchProcessServiceImpl(
    private val komoranService: KomoranService,
    private val clovaService: ClovaService,
    private val alarmService: AlarmService,
    private val jdbcTemplate: JdbcTemplate
): BatchProcessService {

    private val log = Logger.getLogger(this::class.java.toString())

    @Async
    override fun process(post: BatchPost): BatchWritePost {
        var writePost = BatchWritePost(
            post
        )

        //Komoran 통해 주요 단어 추출
        val tags = komoranService.extractContent(post.title)
        writePost.addTagList(tags)
        post.content?.let {
            //Clova를 통해 Content 요약
            val short = clovaService.extractContent(post.title, post.content!!)
            short?.let {
                writePost.insertShortContent(it)
            }
        }
//        //SNS Upload
//        //tistory
//        val out = tistorySnsService.upload(writePost.title, writePost.shrtCnt!!, writePost.tags!!)
//        println(out)
//        //Alarm
//        alarmService.sendAlarm(writePost.title)

        log.info("[NEW]: ${writePost.title}")

        jdbcTemplate.update("INSERT INTO POST(" +
                "${JobConfiguration.TITLE_COLUMN}, " +
                "${JobConfiguration.URL_COLUMN}, " +
                "${JobConfiguration.CREATE_AT_COLUMN}, " +
                "${JobConfiguration.CONTENT_COLUMN}, " +
                "${JobConfiguration.SHORT_CNT_COLUMN}, " +
                "${JobConfiguration.TAGS_COLUMN}, " +
                "${JobConfiguration.IMG_COLUMN}) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) ",
            writePost.title, writePost.url, writePost.create_at, writePost.content, writePost.short_content, writePost.tags, writePost.img)

        return writePost
    }

}