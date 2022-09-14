package com.info.info_v1_backend.infra.mail

import com.info.info_v1_backend.infra.mail.env.MailProperty
import com.info.info_v1_backend.infra.mail.execption.MailSendingException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import javax.mail.MessagingException
import javax.mail.internet.MimeMessage

@Service
class MailUtil (
    private val jms: JavaMailSender,
    private val templateEngine: TemplateEngine,
    private val mailProperty: MailProperty
) {


    fun sendHtmlMail(to: String, title: String, templatePath: String, models: Map<String, Any>) {
        val message: MimeMessage = jms.createMimeMessage()
        var context = Context()
        models.forEach(context::setVariable)
        val content: String = templateEngine.process(templatePath, context)

        try {
            val helper = MimeMessageHelper(message, true, "UTF-8")
            helper.setFrom(mailProperty.username)
            helper.setSubject(title)
            helper.setTo(to)
            helper.setText(content, true)
        } catch (e: MessagingException) {
            throw MailSendingException("SMTP 를 통해 메일 발송중 오류가 발생하였습니다!")
        }
        jms.send(message)
    }




}