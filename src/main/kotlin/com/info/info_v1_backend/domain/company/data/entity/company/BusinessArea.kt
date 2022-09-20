package com.info.info_v1_backend.domain.company.data.entity.company

import javax.persistence.*

@Entity
class BusinessArea(
    name: String,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToMany(mappedBy = "businessArea", cascade = [CascadeType.REMOVE])
    var businessTaggedList: MutableList<BusinessTagged> = ArrayList()
        protected set


}