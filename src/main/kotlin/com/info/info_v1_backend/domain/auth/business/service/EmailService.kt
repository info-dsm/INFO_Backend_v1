package com.info.info_v1_backend.domain.auth.business.service

interface EmailService {
    fun sendCodeToSchoolEmail(email: String)
    fun sendCodeToCompanyEmail(email: String)
    fun sendPasswordCodeToEmail(email: String)
    fun sendChangeEmailCodeToEmail(email: String)
}