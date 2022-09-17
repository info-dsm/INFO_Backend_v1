package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.company.business.dto.request.RegisterCompanyRequest
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.company.Contactor
import com.info.info_v1_backend.domain.company.data.repository.CompanyCheckCodeRepository
import com.info.info_v1_backend.domain.company.data.repository.CompanyRepository
import com.info.info_v1_backend.domain.company.exception.InvalidCompanyCheckCodeException
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class CompanyServiceImpl(
    private val companyRepository: CompanyRepository,
    private val companyCheckCodeRepository: CompanyCheckCodeRepository
): CompanyService {

    override fun registerCompany(request: RegisterCompanyRequest) {
        //checkEmailCode 로직 추가

        val companyCode = companyCheckCodeRepository.findById(request.companyCheckCode).orElse(null)
            ?: throw InvalidCompanyCheckCodeException(request.companyCheckCode)
        companyCode.makeUsed()

        //사업자 등록번호 조회 인증 로직 추가 https://www.data.go.kr/iim/api/selectAPIAcountView.do#/

        companyRepository.save(
            Company(
                request.shortName,
                request.fullName,
                request.companyNumber,
                Contactor(
                    request.contactor.name,
                    request.contactor.position,
                    request.contactor.phoneNum,
                    request.contactor.email,
                    request.contactor.faxAddress,
                    request.contactor.password
                ),
                request.establishedAt,
                request.annualSales,
                request.workerCount,
                request.industryType,
                request.mainProduct,
                request.introduction
            )
        )
        
    }


}