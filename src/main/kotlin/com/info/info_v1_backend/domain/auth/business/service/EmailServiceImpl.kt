package com.info.info_v1_backend.domain.auth.business.service

import com.info.info_v1_backend.domain.auth.data.entity.token.CheckEmailCode
import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.auth.data.repository.token.CheckEmailCodeRepository
import com.info.info_v1_backend.domain.auth.data.repository.user.UserRepository
import com.info.info_v1_backend.domain.auth.exception.UserAlreadyExists
import com.info.info_v1_backend.infra.mail.MailUtil
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class EmailServiceImpl(
    private val mailSender: MailUtil,
    private val checkEmailCodeRepository: CheckEmailCodeRepository,
    private val userRepository: UserRepository<User>
):EmailService {

    companion object{
        const val CODE_LENGTH = 4
        const val AUTH_MAIL_TITLE = "[인증번호]"
    }

    private fun sendCode(email: String){
        val random = RandomStringUtils.randomNumeric(CODE_LENGTH)
        val emailCheckCode = CheckEmailCode(email, random)
        checkEmailCodeRepository.save(emailCheckCode)
        val map = HashMap<String, String>()
        map["code"] = random
        mailSender.sendHtmlMail(email, AUTH_MAIL_TITLE, "mail.html", map)
    }

    override fun sendCodeToEmail(email: String) {
        if(userRepository.existsByEmail(email)){
            throw UserAlreadyExists(email)
        }
        sendCode(email)
    }

    override fun sendPasswordCodeToEmail(email: String) {
        sendCode(email)
    }
}