package com.info.info_v1_backend.global.util.textParsing

import org.springframework.stereotype.Component

@Component
class ContentTextParsingUtilImpl: ContentTextParsingUtil {

    override fun extractLink(content: String): String {
        var removed = content
        var start = 0
        var end = 0

        while (removed.contains("https://")) {
            start = removed.indexOf("https://", start)
            end = removed.indexOf("\n", start)
            removed = removed.removeRange(start, end)
        }
        return removed
    }

    override fun getLinkList(str: String): List<String> {
        var rawStr = str
        val linkList: MutableList<String> = ArrayList<String>()
        var link: String
        var start = 0
        var end = 0

        while (rawStr.contains("https://")) {
            start = rawStr.indexOf("https://", start)
            end = rawStr.indexOf("\n", start).takeUnless { it == -1 }?: break
            link = rawStr.substring(start, end)
            linkList.add(link)
            rawStr = rawStr.removeRange(start, end)
        }
        return linkList
    }

}