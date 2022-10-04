package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.Teacher
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.data.repository.user.UserRepository
import com.info.info_v1_backend.domain.auth.exception.UserNotFoundException
import com.info.info_v1_backend.domain.company.business.dto.request.company.EditCompanyRequest
import com.info.info_v1_backend.domain.company.business.dto.response.company.BusinessAreaResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyWithIsWorkingResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse
import com.info.info_v1_backend.domain.company.business.dto.response.notice.MinimumNoticeResponse
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.company.CompanySearchDocument
import com.info.info_v1_backend.domain.company.data.entity.company.file.BusinessRegisteredCertificateFile
import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyIntroductionFile
import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyLogoFile
import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyPhotoFile
import com.info.info_v1_backend.domain.company.data.entity.company.work.field.FieldTraining
import com.info.info_v1_backend.domain.company.data.entity.company.work.field.FieldTrainingIdClass
import com.info.info_v1_backend.domain.company.data.repository.company.*
import com.info.info_v1_backend.domain.company.data.repository.notice.NoticeRepository
import com.info.info_v1_backend.domain.company.data.repository.notice.NoticeSearchDocumentRepository
import com.info.info_v1_backend.domain.company.exception.*
import com.info.info_v1_backend.global.error.common.NoAuthenticationException
import com.info.info_v1_backend.global.file.exception.FileNotFoundException
import com.info.info_v1_backend.global.file.repository.FileRepository
import com.info.info_v1_backend.global.util.user.CurrentUtil
import com.info.info_v1_backend.infra.amazon.s3.S3Util
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Year
import javax.transaction.Transactional

@Service
@Transactional
class CompanyServiceImpl(
    private val companyRepository: CompanyRepository,
    private val studentRepository: UserRepository<Student>,
    private val companySearchDocumentRepository: CompanySearchDocumentRepository,
    private val noticeSearchDocumentRepository: NoticeSearchDocumentRepository,
    private val s3Util: S3Util,
    private val businessRegisteredCertificateFileRepository: FileRepository<BusinessRegisteredCertificateFile>,
    private val companyIntroductionFileRepository: FileRepository<CompanyIntroductionFile>,
    private val companyLogoFileRepository: FileRepository<CompanyLogoFile>,
    private val companyPhotoRepository: FileRepository<CompanyPhotoFile>,
    private val hiredStudentRepository: HiredStudentRepository,
    private val noticeRepository: NoticeRepository,
    private val businessAreaRepository: BusinessAreaRepository
): CompanyService {

    override fun editCompany(user: User, request: EditCompanyRequest, companyId: Long) {

        if (user is Company) {
            user.editCompany(
                request
            )
            request.companyName?. let { name: String ->
                {
                    companySearchDocumentRepository.findByCompanyId(user.id!!).orElse(null)
                        ?.let { companySearchDocument: CompanySearchDocument ->
                            {
                                companySearchDocument.changeCompanyName(name)
                            }
                        }
                        ?: companySearchDocumentRepository.save(
                            CompanySearchDocument(
                                user.name,
                                user.id!!
                            )
                        )
                    noticeSearchDocumentRepository.findAllByCompanyId(user.id!!).map {
                        noticeSearchDocument -> {
                            noticeSearchDocument.editCompanyName(request.companyName)
                        }
                    }
                }
            }
        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun getMinimumCompanyList(idx: Int, size: Int): Page<MinimumCompanyResponse> {
        return companyRepository.findAll(PageRequest.of(idx, size, Sort.by("created_date")
            .descending())).map {
            it.toMinimumCompanyResponse()
        }
    }

    override fun getMaximumCompany(id: Long): MaximumCompanyResponse {
        return (companyRepository.findByIdOrNull(id)
            ?: throw CompanyNotFoundException(id.toString()))
            .toMaximumCompanyResponse()
    }

    override fun getEntireMaximumCompanyByUserId(user: User, id: Long): List<MaximumCompanyWithIsWorkingResponse> {
        if (user is Student) {
            if (user.id != id) throw NoAuthenticationException(user.roleList.toString())
            val result: MutableList<MaximumCompanyWithIsWorkingResponse> = ArrayList()
            hiredStudentRepository.findAllByStudent(user).map {
                companyRepository.findAllByHiredStudentListContains(it).map {
                    result.add(
                        it.toMaximumCompanyWithIsWorkingResponse()
                    )
                }
            }
            return result
        } else {
            val student = studentRepository.findByIdOrNull(id)
                ?: throw UserNotFoundException(id.toString())
            
            val result: MutableList<MaximumCompanyWithIsWorkingResponse> = ArrayList()
            hiredStudentRepository.findAllByStudent(student).map {
                companyRepository.findAllByHiredStudentListContains(it).map {
                    result.add(
                        it.toMaximumCompanyWithIsWorkingResponse()
                    )
                }
            }
            return result
        }
    }

    override fun searchCompany(query: String): List<MinimumCompanyResponse> {
        val companySearchDocumentList = companySearchDocumentRepository.findByCompanyNameOrderByTextScoreDesc(query, PageRequest.of(0, 20))
            .toList()

        val minimumCompanyResponseList: MutableList<MinimumCompanyResponse> = ArrayList()
        companySearchDocumentList.map {
            companySearchDocument: CompanySearchDocument ->
            companyRepository.findById(companySearchDocument.companyId).orElse(null)?. let{
                company:Company ->
                minimumCompanyResponseList.add(
                    company.toMinimumCompanyResponse()
                )
            }

        }
        return minimumCompanyResponseList
    }

    override fun getBusinessRegisteredCertificate(user: User, companyId: Long): BusinessRegisteredCertificateFile {
        if (user is Student) throw NoAuthenticationException(user.roleList.toString())
        else {
            return companyRepository.findByIdOrNull(companyId)?. let {
                return it.companyIntroduction.getBusinessRegisteredCertificateResponse()
            }?: throw CompanyNotFoundException(companyId.toString())
        }
    }

    override fun getNoticeRegisteredCompanyListByYear(user: User, year: Year, idx: Int, size: Int): Page<MinimumCompanyResponse> {
        return companyRepository.findAllByNoticeRegisteredYearListContains(
            year.value,
        PageRequest.of(idx, size, Sort.by("created_at").descending())).map {
            it.toMinimumCompanyResponse()
        }
    }

    override fun getBusinessArea(): List<BusinessAreaResponse> {
        return businessAreaRepository.findAll().map {
            it.toBusinessAreaResponse()
        }
    }

    override fun changeBusinessRegisteredCertificate(user: User, multipartFile: MultipartFile) {
        if (user is Company) {
            user.let {
                user.companyIntroduction.businessRegisteredCertificate?.let { it1 ->
                    businessRegisteredCertificateFileRepository.delete(
                        it1
                    )
                }

                user.companyIntroduction.changeBusinessRegisteredCertificate(
                    BusinessRegisteredCertificateFile(
                        s3Util.uploadFile(multipartFile, "company/${user.id!!}", "business_registered_file"),
                        user
                    )
                )
            }
        }
        throw NoAuthenticationException(user.roleList.toString())
    }

    override fun addCompanyIntroductionFile(user: User, multipartFile: MultipartFile) {
        if (user is Company) {
            user.let {
                user.companyIntroduction.addCompanyIntroduction(
                    CompanyIntroductionFile(
                        s3Util.uploadFile(multipartFile, "company/${user.id!!}", "business_registered_file"),
                        user
                    )
                )
            }
        }
        throw NoAuthenticationException(user.roleList.toString())
    }

    override fun removeCompanyIntroductionFile(user: User, fileId: Long) {
        if (user is Company) {
            user.let {
                user.companyIntroduction.removeCompanyIntroduction(
                    companyIntroductionFileRepository.findByIdOrNull(fileId)?: throw FileNotFoundException(fileId.toString())
                )
            }
        }
        throw NoAuthenticationException(user.roleList.toString())
    }

    override fun changeCompanyLogo(user: User, multipartFile: MultipartFile) {
        if (user is Company) {
            user.let {
                user.companyIntroduction.companyLogo?.let { it1 ->
                    companyLogoFileRepository.delete(
                        it1
                    )
                }
                user.companyIntroduction.changeCompanyLogo(
                    CompanyLogoFile(
                        s3Util.uploadFile(multipartFile, "company/${user.id!!}", "company_introduction_file"),
                        user
                    )
                )
            }
        }
        throw NoAuthenticationException(user.roleList.toString())
    }

    override fun addCompanyPhoto(user: User, multipartFile: MultipartFile) {
        if (user is Company) {
            user.let {
                user.companyIntroduction.addCompanyPhoto(
                    CompanyPhotoFile(
                        s3Util.uploadFile(multipartFile, "company/${user.id!!}", "company_photo"),
                        user
                    )
                )
            }
        }
        throw NoAuthenticationException(user.roleList.toString())
    }

    override fun removeCompanyPhoto(user: User, fileId: Long) {
        if (user is Company) {
            user.let {
                companyPhotoRepository.delete(
                    companyPhotoRepository.findByIdOrNull(fileId)?: throw FileNotFoundException(fileId.toString())
                )

                user.companyIntroduction.removeCompanyPhoto(
                    companyPhotoRepository.findByIdOrNull(fileId)?: throw FileNotFoundException(fileId.toString())
                )
            }
        }
        throw NoAuthenticationException(user.roleList.toString())
    }

    override fun makeAssociated(user: User, companyId: Long) {
        if (user is Teacher) {
            (companyRepository.findByIdOrNull(companyId)?: throw CompanyNotFoundException(companyId.toString()))
                .makeAssociated()
        }
        throw NoAuthenticationException(user.roleList.toString())
    }


}