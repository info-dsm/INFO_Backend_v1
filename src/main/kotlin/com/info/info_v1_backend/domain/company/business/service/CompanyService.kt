package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.company.EditCompanyRequest
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse
import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

interface CompanyService {

    fun editCompany(user: User, request: EditCompanyRequest)
    fun getMinimumCompanyList(idx: Int, size: Int): Page<MinimumCompanyResponse>
    fun getMaximumCompany(id: Long): MaximumCompanyResponse
    fun getEntireMaximumCompanyByUserId(user: User, id: Long): List<MaximumCompanyResponse>
    fun searchCompany(query: String): List<MinimumCompanyResponse>

    //photo
    fun changeBusinessRegisteredCertificate(user: User, multipartFile: MultipartFile)

    fun addCompanyIntroductionFile(user: User, multipartFile: MultipartFile)
    fun removeCompanyIntroductionFile(user: User, fileId: Long)

    fun changeCompanyLogo(user: User, multipartFile: MultipartFile)

    fun addCompanyPhoto(user: User, multipartFile: MultipartFile)
    fun removeCompanyPhoto(user: User, fileId: Long)

    //Associate
    fun makeAssociated(user: User, companyId: Long)

}