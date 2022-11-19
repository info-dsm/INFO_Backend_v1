package com.info.info_v1_backend.domain.company.business.service

import com.info.info_v1_backend.domain.auth.business.dto.request.CompanySignupRequest
import com.info.info_v1_backend.domain.auth.business.dto.request.EditPasswordRequest
import com.info.info_v1_backend.domain.auth.business.dto.request.LoginRequest
import com.info.info_v1_backend.domain.auth.business.service.AuthService
import com.info.info_v1_backend.domain.auth.business.service.EmailService
import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.Teacher
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.data.repository.user.UserRepository
import com.info.info_v1_backend.domain.auth.exception.IsNotStudentException
import com.info.info_v1_backend.domain.auth.exception.UserNotFoundException
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyLoginRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.EditCompanyPasswordRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.EditCompanyRequest
import com.info.info_v1_backend.domain.company.business.dto.response.company.BusinessAreaResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyWithIsWorkingResponse
import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.company.CompanySearchDocument
import com.info.info_v1_backend.domain.company.data.entity.company.embeddable.CompanyIntroduction
import com.info.info_v1_backend.domain.company.data.entity.company.file.BusinessRegisteredCertificateFile
import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyIntroductionFile
import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyLogoFile
import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyPhotoFile
import com.info.info_v1_backend.domain.company.data.entity.company.tag.BusinessArea
import com.info.info_v1_backend.domain.company.data.entity.company.tag.BusinessAreaTagged
import com.info.info_v1_backend.domain.company.data.repository.company.*
import com.info.info_v1_backend.domain.company.data.repository.notice.NoticeSearchDocumentRepository
import com.info.info_v1_backend.domain.company.exception.*
import com.info.info_v1_backend.global.error.common.ForbiddenException
import com.info.info_v1_backend.global.error.common.NoAuthenticationException
import com.info.info_v1_backend.global.file.exception.FileNotFoundException
import com.info.info_v1_backend.global.file.repository.FileRepository
import com.info.info_v1_backend.global.security.jwt.data.TokenResponse
import com.info.info_v1_backend.infra.amazon.s3.S3Util
import com.mongodb.MongoQueryException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.UncategorizedMongoDbException
import org.springframework.data.mongodb.core.query.TextCriteria
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.Year
import javax.transaction.Transactional

@Service
@Transactional
class CompanyServiceImpl(
    private val companyRepository: CompanyRepository,
    private val emailService: EmailService,
    private val authService: AuthService,
    private val userRepository: UserRepository<User>,
    private val companySearchDocumentRepository: CompanySearchDocumentRepository,
    private val noticeSearchDocumentRepository: NoticeSearchDocumentRepository,
    private val s3Util: S3Util,
    private val businessRegisteredCertificateFileRepository: FileRepository<BusinessRegisteredCertificateFile>,
    private val companyIntroductionFileRepository: FileRepository<CompanyIntroductionFile>,
    private val companyLogoFileRepository: FileRepository<CompanyLogoFile>,
    private val hiredStudentRepository: HiredStudentRepository,
    private val businessAreaRepository: BusinessAreaRepository,
    private val passwordEncoder: PasswordEncoder,
    private val businessAreaTaggedRepository: BusinessAreaTaggedRepository,
    private val companyPhotoFileRepository: FileRepository<CompanyPhotoFile>,
): CompanyService {

    override fun sendCompanyPasswordCode(companyNumber: String): String {
        val company = companyRepository.findByCompanyNumber(companyNumber).orElse(null)?: throw CompanyNotFoundException(companyNumber)
        emailService.sendPasswordCodeToEmail(company.email)
        return company.email
    }

    override fun changeCompanyPassword(e: EditCompanyPasswordRequest) {
        val company: Company = companyRepository.findByEmail(e.email).orElse(null)?: throw CompanyNotFoundException(e.email)
        authService.changePassword(company, EditPasswordRequest(
            e.password,
            e.code
        ))
        company.changePasswordHint(e.passwordHint)
    }

    override fun checkCompanyNumber(companyNumber: String) {
        if (!companyRepository.existsByCompanyNumber(companyNumber)) throw UserNotFoundException(companyNumber)
    }

    override fun getPasswordHint(companyNumber: String): String {
        return (companyRepository.findByCompanyNumber(companyNumber)
            .orElse(null)?: throw CompanyNotFoundException(companyNumber))
            .passwordHint
    }

    override fun changePasswordHint(user: User, newHint: String) {
        if (user is Company) {
            user.changePasswordHint(newHint)
        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun companyLogin(request: CompanyLoginRequest): TokenResponse {
        val company = companyRepository.findByCompanyNumber(request.companyNumber).orElse(null)?: throw CompanyNotFoundException(request.companyNumber)
        return authService.login(
            LoginRequest(
                company.email,
                request.password
            )
        )

    }

    override fun registerCompany(
        req: CompanySignupRequest,
        emailCheckCode: String,
        businessRegisteredCertificate: MultipartFile,
        companyIntroductionFile: List<MultipartFile>,
        companyLogo: MultipartFile,
        companyPhotoList: List<MultipartFile>,
        passwordHint: String
    ) {
        val company = companyRepository.save(
            Company(
                passwordEncoder.encode(req.password),
                req.companyNameRequest,
                req.companyInformation.toCompanyInformation(),
                req.companyContact.toCompanyContact(),
                CompanyIntroduction(req.introduction),
                req.isLeading,
                passwordHint
            )
        )

        saveCompanyRelatedFileList(
            businessRegisteredCertificate,
            companyIntroductionFile,
            companyLogo,
            companyPhotoList,
            company
        )

        req.businessAreaList.map {
            businessAreaTaggedRepository.save(
                BusinessAreaTagged(
                    businessAreaRepository.findByIdOrNull(it)
                        ?: businessAreaRepository.save(
                            BusinessArea(it)
                        ),
                    company
                )
            )
        }

        companySearchDocumentRepository.save(
            CompanySearchDocument(
                company.name,
                company.id!!,
            )
        )
    }

    private fun saveCompanyRelatedFileList(
        businessRegisteredCertificate: MultipartFile,
        companyIntroductionFile: List<MultipartFile>,
        companyLogo: MultipartFile,
        companyPhotoList: List<MultipartFile>,
        company: Company
    ) {
        company.companyIntroduction.registerCompanyLogoAndBusinessCertificate(
            companyLogoFileRepository.save(
                CompanyLogoFile(
                    s3Util.uploadFile(
                        companyLogo,
                        "company/${company.id!!}",
                        "companyLogo"
                    ),
                    company
                )
            ),
            businessRegisteredCertificateFileRepository.save(
                BusinessRegisteredCertificateFile(
                    s3Util.uploadFile(
                        businessRegisteredCertificate,
                        "company/${company.id!!}",
                        "businessRegisteredCertificate"
                    ),
                    company
                )
            )
        )
        companyIntroductionFile.map {
            company.companyIntroduction.addCompanyIntroductionFile(
                companyIntroductionFileRepository.save(
                    CompanyIntroductionFile(
                        s3Util.uploadFile(
                            it,
                            "company/${company.id!!}",
                            "companyIntroduction"
                        ),
                        company
                    )
                )
            )
        }
        companyPhotoList.map {
            company.companyIntroduction.addCompanyPhoto(
                companyPhotoFileRepository.save(
                    CompanyPhotoFile(
                        s3Util.uploadFile(
                            it,
                            "company/${company.id!!}",
                            "companyPhoto"
                        ),
                        company
                    )
                )
            )

        }
    }


    override fun editCompany(
        user: User,
        request: EditCompanyRequest,
        companyId: Long
    ) {
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
        return companyRepository.findAll(PageRequest.of(idx, size, Sort.by("createdAt")
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
        if (user is Company) {
            throw NoAuthenticationException(user.roleList.toString())
        }else if (user is Student) {
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
            val student = userRepository.findByIdOrNull(id)
                ?: throw UserNotFoundException(id.toString())
            if (student is Company || student is Teacher) throw IsNotStudentException("${student.id}, ${student.roleList.toString()}")
            else if (student is Student ) {
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
            throw NoAuthenticationException(user.roleList.toString())
        }
    }

    override fun searchCompany(query: String): Page<MinimumCompanyResponse>? {

        return try {
            companySearchDocumentRepository.findAllBy(
                TextCriteria.forDefaultLanguage().matchingAny(query),
                PageRequest.of(0, 20, Sort.by("createdAt"))
            ).map { it1 ->
                companyRepository.findByIdOrNull(it1.companyId)
                    ?.toMinimumCompanyResponse()
            }
        } catch(e: MongoQueryException) {
            return null
        } catch (e: UncategorizedMongoDbException) {
            return null
        } catch (e: java.lang.NullPointerException) {
            null
        }
    }

    override fun getBusinessRegisteredCertificate(user: User, companyId: Long): BusinessRegisteredCertificateFile {
        if (user is Student) {
            throw NoAuthenticationException(user.roleList.toString())
        } else if (user is Company) {
            if (user.id != companyId) throw NoAuthenticationException("You are not $companyId, you're id is ${user.id}")
            return user.companyIntroduction.businessRegisteredCertificate!!
        } else {
            return companyRepository.findByIdOrNull(companyId)?. let {
                return it.companyIntroduction.getBusinessRegisteredCertificateResponse()
            }?: throw CompanyNotFoundException(companyId.toString())
        }
    }

    override fun getNoticeRegisteredCompanyListByYear(
        user: User,
        year: Year,
        idx: Int,
        size: Int
    ): Page<MinimumCompanyResponse> {
        return companyRepository.findAllByNoticeRegisteredYearListContains(
            year.value,
        PageRequest.of(idx, size, Sort.by("createdAt").descending())).map {
            it.toMinimumCompanyResponse()
        }
    }

    override fun getBusinessAreaList(): List<BusinessAreaResponse> {
        return businessAreaRepository.findAll().map {
            it.toBusinessAreaResponse()
        }
    }

    override fun addBusinessArea(user: User, businessAreaId: String) {
        if (user is Company) {
            businessAreaTaggedRepository.save(
                BusinessAreaTagged(
                    businessAreaRepository.findByIdOrNull(
                        businessAreaId
                    )?: businessAreaRepository.save(
                        BusinessArea(
                            businessAreaId
                        )
                    ),
                    user
                )
            )
        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun removeBusinessArea(user: User, businessAreaId: String) {
        if (user is Company) {

            val area = user.businessAreaTaggedList.firstOrNull {
                it.businessArea.id == businessAreaId
            }?: return

            if(area.company == user){
                businessAreaTaggedRepository.delete(area)
            }else throw ForbiddenException("${user}가 ${area.company}에 권한없음")

        } else throw NoAuthenticationException(user.roleList.toString())
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
                    s3Util.uploadFile(
                        multipartFile,
                        "company/${user.id!!}",
                        "business_registered_file"
                    ),
                        user
                    )
                )
            }
        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun addCompanyIntroductionFile(user: User, multipartFile: MultipartFile) {
        if (user is Company) {
            user.let {
                user.companyIntroduction.addCompanyIntroduction(
                    CompanyIntroductionFile(
                        s3Util.uploadFile(
                            multipartFile,
                            "company/${user.id!!}",
                            "business_registered_file"
                        ),
                        user
                    )
                )
            }
        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun removeCompanyIntroductionFile(user: User, fileId: Long) {

        val file = companyIntroductionFileRepository.findByIdOrNull(fileId)
            ?: throw FileNotFoundException(fileId.toString())

        if (user is Company && file.company == user) {

            user.let {
                it.companyIntroduction.removeCompanyIntroduction(
                    file
                )
                companyIntroductionFileRepository.delete(
                    file
                )
            }

        } else throw ForbiddenException("${user}가 ${file.company}에 접근권한 없음")
    }
    override fun changeCompanyLogo(user: User, multipartFile: MultipartFile) {
        if (user is Company) {
            user.let {
                user.companyIntroduction.companyLogo?.let { it1 ->
                    companyLogoFileRepository.delete(it1)
                }
                user.companyIntroduction.changeCompanyLogo(
                    CompanyLogoFile(
                        s3Util.uploadFile(
                            multipartFile,
                            "company/${user.id!!}",
                            "company_logo_file"
                        ),
                        user
                    )
                )
            }
        } else throw NoAuthenticationException(user.roleList.toString())
    }


    override fun addCompanyPhoto(user: User, multipartFile: MultipartFile) {
        if (user is Company) {
            user.let {
                user.companyIntroduction.addCompanyPhoto(
                    CompanyPhotoFile(
                        s3Util.uploadFile(
                            multipartFile,
                            "company/${user.id!!}",
                            "company_photo_file"
                        ),
                        user
                    )
                )
            }
        } else throw NoAuthenticationException(user.roleList.toString())
    }

    override fun removeCompanyPhoto(user: User, fileId: Long) {
        val file = companyPhotoFileRepository.findByIdOrNull(fileId)
            ?:throw FileNotFoundException(fileId.toString())
        if (user is Company && file.company == user) {
            user.let {
                it.companyIntroduction.removeCompanyPhoto(
                    file
                )
                companyPhotoFileRepository.delete(
                    file
                )
            }
        } else throw ForbiddenException("${user}가 ${file.company}에 접근권한 없음")
    }

    override fun makeAssociated(user: User, companyId: Long) {
        if (user is Teacher) {
            (companyRepository.findByIdOrNull(companyId)
                ?: throw CompanyNotFoundException(companyId.toString()))
                .makeAssociated()
        } else throw NoAuthenticationException(user.roleList.toString())
    }


}