package com.info.info_v1_backend.domain.company.data.entity.company

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.TextIndexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.TextScore


@Document(collection = "company_search")
class CompanySearchDocument(
    companyName: String,
    companyId: String,
) {
    @Id
    val id: ObjectId = ObjectId.get()
    @TextIndexed(weight = 10F)
    var companyName: String = companyName
        protected set


    val companyId: String = companyId

    @TextScore
    @Field(name = "text_score")
    val textScore: Float? = null

    fun changeCompanyName(newName: String) {
        this.companyName = newName
    }

}