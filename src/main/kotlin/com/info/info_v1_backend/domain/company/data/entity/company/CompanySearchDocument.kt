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
    companyId: Long,
) {
    @Id
    var id: ObjectId = ObjectId.get()
        protected set
    @TextIndexed(weight = 10F)
    var companyName: String = companyName
        protected set

    @Field(name = "company_id")
    val companyId: Long = companyId

    @TextScore
    var textScore: Float? = null
        protected set

    fun changeCompanyName(newName: String) {
        this.companyName = newName
    }

}