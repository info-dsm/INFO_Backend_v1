package com.info.info_v1_backend.domain.auth.business.service

interface EmailService {
    fun sendCodeToEmail(email: String)
}