package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.company.business.dto.request.company.EditCompanyRequest
import com.info.info_v1_backend.domain.company.business.dto.response.company.BusinessAreaResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyWithIsWorkingResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MinimumNoticeResponse
import com.info.info_v1_backend.domain.company.data.entity.company.file.BusinessRegisteredCertificateFile
import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.time.Year

interface CompanyService {

    fun editCompany(user: User, request: EditCompanyRequest, companyId: Long)
    fun getMinimumCompanyList(idx: Int, size: Int): Page<MinimumCompanyResponse>
    fun getMaximumCompany(id: Long): MaximumCompanyResponse
    fun getEntireMaximumCompanyByUserId(user: User, id: Long): List<MaximumCompanyWithIsWorkingResponse>
    fun searchCompany(query: String): List<MinimumCompanyResponse>

    fun getBusinessRegisteredCertificate(user: User, companyId: Long): BusinessRegisteredCertificateFile

    fun getNoticeRegisteredCompanyListByYear(user: User, year: Year, idx: Int, size: Int): Page<MinimumCompanyResponse>

    fun getBusinessArea(): List<BusinessAreaResponse>

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