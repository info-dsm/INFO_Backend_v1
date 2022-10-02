package com.info.info_v1_backend.domain.company.data.entity.company.tag

import com.info.info_v1_backend.domain.company.business.dto.response.company.BusinessAreaResponse
import javax.persistence.*

@Entity
class BusinessArea(
    name: String,
) {

    @Id
    val id: String = name

    @OneToMany(mappedBy = "businessArea", cascade = [CascadeType.REMOVE])
    var businessAreaTaggedList: MutableList<BusinessAreaTagged> = ArrayList()
        protected set


    fun toBusinessAreaResponse(): BusinessAreaResponse {
        return BusinessAreaResponse(
            this.id
        )
    }

}