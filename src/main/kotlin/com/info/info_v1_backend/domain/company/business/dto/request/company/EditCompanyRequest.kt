package com.info.info_v1_backend.domain.company.business.dto.request.company


data class EditCompanyRequest (
    val companyName: String?,
    val companyInformation: CompanyInformationRequest?,
    val companyContact: CompanyContactRequest?,
    val companyIntroduction: CompanyIntroductionRequest?,
    val isLeading: Boolean?,


)