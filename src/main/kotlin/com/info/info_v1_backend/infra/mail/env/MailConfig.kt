package com.info.info_v1_backend.infra.mail.env


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*


@Configuration
class MailConfig(
    private val mailProp: MailProperty
) {

    companion object {
        const val TLS_REQUIRED = "mail.smtp.starttls.required"
        const val TLS_ENABLE = "mail.smtp.starttls.enable"
        const val SMTP_AUTH = "mail.smtp.auth"
        const val SMTP_SOCKET_FACTORY_PORT= "mail.smtp.socketFactory.port"
        const val SMTP_SOCKET_FACTORY_FALlBACK = "mail.smtp.socketFactory.fallback"
        const val TRANSPORT_PROTOCOL="mail.transport.protocol"
    }

    @Bean
    fun mailSender(): JavaMailSender {
        val jms = JavaMailSenderImpl()
        jms.host = mailProp.host
        jms.port = mailProp.port
        jms.username = mailProp.username
        jms.password = mailProp.password
        jms.protocol = mailProp.protocol
        jms.defaultEncoding = "UTF-8"

        val pt = Properties()
        pt.put(TLS_REQUIRED, true)
        pt.put(TLS_ENABLE, true)
        pt.put(SMTP_AUTH, true)
        pt.put(SMTP_SOCKET_FACTORY_PORT, 587);
        pt.put(SMTP_SOCKET_FACTORY_FALlBACK, false);
        pt.put(TRANSPORT_PROTOCOL, mailProp.protocol);
        pt.put("mail.debug", true)

        jms.javaMailProperties = pt
        return jms
    }

}