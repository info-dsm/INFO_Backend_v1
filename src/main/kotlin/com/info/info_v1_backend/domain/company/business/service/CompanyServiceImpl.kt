package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.company.business.dto.request.company.EditCompanyRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.RegisterCompanyRequest
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.auth.data.entity.user.Contactor
import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.data.repository.user.UserRepository
import com.info.info_v1_backend.domain.auth.exception.StudentCannotOpenException
import com.info.info_v1_backend.domain.auth.exception.UserNotFoundException
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse
import com.info.info_v1_backend.domain.company.data.repository.company.CompanyCheckCodeRepository
import com.info.info_v1_backend.domain.company.data.repository.company.CompanyRepository
import com.info.info_v1_backend.domain.company.exception.CompanyNotFoundException
import com.info.info_v1_backend.domain.company.exception.InvalidCompanyCheckCodeException
import com.info.info_v1_backend.domain.company.exception.IsNotContactorCompany
import com.info.info_v1_backend.domain.company.exception.NotContactorException
import com.info.info_v1_backend.global.util.user.CurrentUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class CompanyServiceImpl(
    private val companyRepository: CompanyRepository,
    private val companyCheckCodeRepository: CompanyCheckCodeRepository,
    private val currentUtil: CurrentUtil,
    private val studentRepository: UserRepository<Student>
): CompanyService {

    override fun registerCompany(request: RegisterCompanyRequest) {
        //checkEmailCode 로직 추가

        val companyCode = companyCheckCodeRepository.findById(request.companyCheckCode).orElse(null)
            ?: throw InvalidCompanyCheckCodeException(request.companyCheckCode)
        companyCode.makeUsed()

        //사업자 등록번호 조회 인증 로직 추가 https://www.data.go.kr/iim/api/selectAPIAcountView.do#/

        val current: User = currentUtil.getCurrentUser()

        if (current !is Contactor) throw NotContactorException(current.roleList.toString())

        companyRepository.save(
            Company(
                request.shortName,
                request.fullName,
                request.companyNumber,
                request.companyPhone,
                request.faxAddress,
                current,
                request.establishedAt,
                request.annualSales,
                request.workerCount,
                request.industryType,
                request.mainProduct,
                request.introduction
            )
        )
        
    }

    override fun editCompany(request: EditCompanyRequest, id: Long) {
        val current = currentUtil.getCurrentUser()

        if (current is Contactor) {
            val company = companyRepository.findById(id).orElse(null)?: throw CompanyNotFoundException(id.toString())
            if (company != current.company) throw IsNotContactorCompany(current.email)
            company.editCompany(
                request
            )
        } else throw NotContactorException(current.roleList.toString())
    }

    override fun getMinimumCompanyList(idx: Int, size: Int): Page<MinimumCompanyResponse> {
        return companyRepository.findAll(PageRequest.of(idx, size, Sort.by("created_date").descending())).map {
            it.toMinimumCompanyResponse()
        }
    }

    override fun getMaximumCompany(id: Long): MaximumCompanyResponse {
        return (companyRepository.findById(id).orElse(null)?: throw CompanyNotFoundException(id.toString()))
            .toMaximumCompanyResponse()

    }

    override fun getMaximumCompanyByUserId(id: Long): List<MaximumCompanyResponse> {
        val current = currentUtil.getCurrentUser()

        if (current is Student) {
            if (current.id != id) throw StudentCannotOpenException(current.email)
            return companyRepository.findAllByStudentListContains(current).map {
                it.toMaximumCompanyResponse()
            }
        } else {
            val student = studentRepository.findById(id).orElse(null)?: throw UserNotFoundException(id.toString())
            return companyRepository.findAllByStudentListContains(student).map {
                it.toMaximumCompanyResponse()
            }
        }
    }


}