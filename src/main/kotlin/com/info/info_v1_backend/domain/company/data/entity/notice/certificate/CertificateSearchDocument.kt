package com.info.info_v1_backend.domain.company.data.entity.notice.certificate

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.TextIndexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.TextScore

@Document(collection = "certificate_search")
class CertificateSearchDocument(
    certificateName: String
) {
    @Id
    val id: ObjectId = ObjectId.get()

    @TextIndexed(weight = 10F)
    var certificateName: String = certificateName
        protected set

    @TextScore
    @Field(name = "text_score")
    val textScore: Float? = null

}