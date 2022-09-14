package com.info.info_v1_backend.global.util.textParsing

interface ContentTextParsingUtil {

    fun extractLink(content: String): String
    fun getLinkList(str: String): List<String>?

}