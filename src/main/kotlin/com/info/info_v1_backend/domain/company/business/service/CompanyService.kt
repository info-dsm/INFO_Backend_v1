package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.company.business.dto.request.company.EditCompanyRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.RegisterCompanyRequest
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse
import org.springframework.data.domain.Page

interface CompanyService {

    fun addContactor(newContactorEmail: String)
    fun removeContactor(targetContactorEmail: String)
    fun registerCompany(request: RegisterCompanyRequest)
    fun editCompany(request: EditCompanyRequest, companyId: String)
    fun getMinimumCompanyList(idx: Int, size: Int): Page<MinimumCompanyResponse>
    fun getMaximumCompany(id: String): MaximumCompanyResponse
    fun getMaximumCompanyByUserId(id: Long): List<MaximumCompanyResponse>
    fun searchCompany(query: String): List<MinimumCompanyResponse>
}