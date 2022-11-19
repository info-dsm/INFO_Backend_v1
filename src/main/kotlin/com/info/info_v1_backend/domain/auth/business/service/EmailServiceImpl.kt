package com.info.info_v1_backend.domain.auth.business.service

import com.info.info_v1_backend.domain.auth.data.entity.token.CheckCompanyEmailCode
import com.info.info_v1_backend.domain.auth.data.entity.token.CheckEmailCode
import com.info.info_v1_backend.domain.auth.data.entity.token.CheckPasswordCode
import com.info.info_v1_backend.domain.auth.data.entity.token.CheckSchoolEmailCode
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.data.repository.token.CheckCompanyEmailCodeRepository
import com.info.info_v1_backend.domain.auth.data.repository.token.CheckEmailCodeRepository
import com.info.info_v1_backend.domain.auth.data.repository.token.CheckPasswordCodeRepository
import com.info.info_v1_backend.domain.auth.data.repository.token.CheckSchoolEmailCodeRepository
import com.info.info_v1_backend.domain.auth.data.repository.user.UserRepository
import com.info.info_v1_backend.domain.auth.exception.UserAlreadyExists
import com.info.info_v1_backend.infra.mail.MailUtil
import org.apache.commons.lang3.RandomStringUtils
import org.apache.http.auth.AUTH
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class EmailServiceImpl(
    private val mailSender: MailUtil,
    private val checkEmailCodeRepository: CheckEmailCodeRepository,
    private val checkSchoolEmailCodeRepository: CheckSchoolEmailCodeRepository,
    private val checkCompanyEmailCodeRepository: CheckCompanyEmailCodeRepository,
    private val checkPasswordCodeRepository: CheckPasswordCodeRepository,
    private val userRepository: UserRepository<User>
):EmailService {

    companion object{
        const val CODE_LENGTH = 4
        const val AUTH_MAIL_TITLE = "[인증번호]"
    }
    private fun sendMail(email: String, random: String){
        val map = HashMap<String, String>()
        map["code"] = random
        mailSender.sendHtmlMail(email, AUTH_MAIL_TITLE, "mail.html", map)
    }

    override fun sendCodeToSchoolEmail(email: String) {
        userRepository.findByEmail(email).orElse(null)?.let { throw UserAlreadyExists(email) }
        val random = RandomStringUtils.randomNumeric(CODE_LENGTH)
        val emailCheckCode = CheckSchoolEmailCode(email, random)
        checkSchoolEmailCodeRepository.save(emailCheckCode)
        sendMail(email,random)
    }

    override fun sendCodeToCompanyEmail(email: String) {
        userRepository.findByEmail(email).orElse(null)?.let { throw UserAlreadyExists(email) }
        val random = RandomStringUtils.randomNumeric(CODE_LENGTH)
        val emailCheckCode = CheckCompanyEmailCode(email, random)
        checkCompanyEmailCodeRepository.save(emailCheckCode)
        val code = checkCompanyEmailCodeRepository.findByIdOrNull(email)
        sendMail(email,random)
    }

    override fun sendPasswordCodeToEmail(email: String) {
        val random = RandomStringUtils.randomNumeric(CODE_LENGTH)
        val emailCheckCode = CheckPasswordCode(email, random)
        checkPasswordCodeRepository.save(emailCheckCode)
        sendMail(email,random)
    }

    override fun sendChangeEmailCodeToEmail(email: String) {
        val random = RandomStringUtils.randomNumeric(CODE_LENGTH)
        val emailCheckCode = CheckEmailCode(email, random)
        checkEmailCodeRepository.save(emailCheckCode)
        sendMail(email, random)
    }

}