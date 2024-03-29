package com.info.info_v1_backend.infra.mail.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("mail")
@ConstructorBinding
data class MailProperty(
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val protocol: String,
    val properties: Properties

) {

    data class Properties(
        val mail: Mail
    )

    data class Mail(
        val smtp: Smtp
    )

    data class Smtp(
        val auth: Boolean
    )

    data class Starttls(
        val enable: Boolean
    )

}
