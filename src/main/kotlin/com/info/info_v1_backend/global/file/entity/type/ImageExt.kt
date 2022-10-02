package com.info.info_v1_backend.global.file.entity.type

enum class ImageExt(
    val extension: String,
    val contentType: String
) {
    JPG("jpg", "image/jpeg"),
    JPEG("jpeg", "image/jpeg"),
    PNG("png", "image/png")
}