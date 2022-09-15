package com.info.info_v1_backend.global.batch.data.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table



class BatchPost(
    title: String,
    var url: String,
    var create_at: String,
    var content: String?,
    var img: String?
) {
    var title = title

}