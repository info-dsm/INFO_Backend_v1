package com.info.info_v1_backend.global.batch.data.entity


class BatchWritePost(
    post: BatchPost
) {

    var title = post.title

    var url: String = post.url
    var create_at: String = post.create_at
    var content: String? = post.content
    var tags: String? = null

    var short_content: String? = null

    var img: String? = post.img

    fun addTagList(tags: List<String>) {
        var str = ""
        tags.forEach {
            str += ("$it ")
        }
        this.tags = str
    }

    fun insertShortContent(short: String) {
        this.short_content = short
    }

    override fun toString(): String {
        return "${this.title}, ${this.url}"
    }
}