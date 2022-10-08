package com.info.info_v1_backend.domain.auth.business.service

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.auth.data.entity.user.Teacher
import com.info.info_v1_backend.domain.auth.exception.UserNotFoundException
import com.info.info_v1_backend.domain.auth.business.dto.request.EditStudentInfoRequest
import com.info.info_v1_backend.domain.auth.business.dto.response.*
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.data.repository.user.StudentRepository
import com.info.info_v1_backend.domain.auth.data.repository.user.UserRepository
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.repository.company.FieldTrainingRepository
import com.info.info_v1_backend.domain.company.data.repository.company.HiredStudentRepository
import com.info.info_v1_backend.global.error.common.NoAuthenticationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@Service
class UserServiceImpl(
    private val userRepository: UserRepository<User>,
    private val studentRepository: StudentRepository,
    private val fieldTrainingRepository: FieldTrainingRepository,
    private val hiredStudentRepository: HiredStudentRepository,
): UserService {

    override fun deleteMe(user: User) {
        userRepository.delete(user)
    }

    override fun getUserInfo(user: User, userId: Long?): UserInfoResponse {
            return when (user) {
                is Teacher -> {
                    userId?. let {
                        val target: User = userRepository.findByIdOrNull(it)?: throw UserNotFoundException(it.toString())
                        return@let TeacherInfoResponse(
                            target.name,
                            target.email
                        )
                    }?: TeacherInfoResponse(
                        user.name,
                        user.email
                    )
                }

                is Company -> {
                    CompanyResponse(
                        user.name,
                        user.email,
                        user.companyContact.contactorRank,
                        user.companyContact.contactorPhone,
                        user.toMinimumCompanyResponse()
                    )
                }
                is Student -> {
                    user.toStudentInfoResponse()
                }
                else -> {
                    throw UserNotFoundException(user.email)
                }

        }
    }

    override fun getStudentList(user: User, idx: Int, size: Int): Page<MinimumStudent> {
        if (user is Student) throw NoAuthenticationException(user.roleList.toString())
        return studentRepository.findAll(PageRequest.of(idx, size, Sort.by("createdAt").descending()))
            .map {
                it.toMinimumStudent()
            }
    }


}