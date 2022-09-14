package com.info.info_v1_backend.global.batch.service

import com.info.info_v1_backend.global.batch.data.entity.BatchPost
import com.info.info_v1_backend.global.batch.data.entity.BatchWritePost

interface BatchProcessService {

    fun process(post: BatchPost): BatchWritePost

}