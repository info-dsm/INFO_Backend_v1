package com.info.info_v1_backend.domain.company.business.dto.request

import java.time.Year
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class RegisterCompanyRequest(
    @field:Size(max = 30, min = 1)
    val shortName: String,
    @field:Size(max = 50, min = 1)
    val fullName: String,
    @field:Pattern(regexp = "/([0-9]{3})-?([0-9]{2})-?([0-9]{5})/")
    val companyNumber: String,
    @field:Pattern(
        regexp = "/^(o2|0[0-9]{2})-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})\$/",
        message = "전화번호는 반드시 02 or 0xx-xxxx-xxxx 조합이여야합니다."
    )
    val companyPhone: String,
    @field:Size(min = 30, max = 5, message = "fax번호 길이는 5 ~ 30자여야합니다.")
    val faxAddress: String?,
    val contactor: ContactorDto,
    val establishedAt: Year,
    @field:Size(max = 20)
    val annualSales: Long,
    @field:Max(100000)
    val workerCount: Int,
    val industryType: String?,
    val mainProduct: String?,
    @field:Size(
        max = 255,
        min = 10,
        message = "회사 소개는 10자 이상이여야합니다."
    )
    val introduction: String,
    @field:Size(min = 6, max = 6)
    val companyCheckCode: String,
    @field:Size(min = 4, max = 4)
    val emailCheckCode: String,


)
