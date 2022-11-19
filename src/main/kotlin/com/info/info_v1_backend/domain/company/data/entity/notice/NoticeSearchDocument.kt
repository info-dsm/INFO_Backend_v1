package com.info.info_v1_backend.domain.company.data.entity.notice

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.TextIndexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "notice_search")
class NoticeSearchDocument(
    businessInformation: String,
    noticeId: Long,
    companyName: String,
    companyId: String
) {

    @Id
    var id: ObjectId = ObjectId.get()
        protected set

    @TextIndexed(weight = 10F)
    var businessInformation: String = businessInformation
        protected set

    @TextIndexed(weight = 8F)
    var companyName: String = companyName
        protected set

    @Field(name = "company_id")
    var companyId: String = companyId
        protected set

    val noticeId: Long = noticeId

    fun editCompanyName(name: String) {
        this.companyName = name
    }

    fun editBusinessInfo(newInfo: String?) {
        newInfo?.let{
            this.businessInformation = it
        }
    }

}