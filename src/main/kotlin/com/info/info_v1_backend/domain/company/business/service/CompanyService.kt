package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.company.business.dto.request.RegisterCompanyRequest

interface CompanyService {

    fun registerCompany(request: RegisterCompanyRequest)
}