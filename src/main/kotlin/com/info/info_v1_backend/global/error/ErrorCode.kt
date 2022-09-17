package com.info.info_v1_backend.global.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val message: String,
    val status: HttpStatus
) {

    USERNAME_NOT_FOUND("Username Not Found", HttpStatus.NOT_FOUND),
    INVALID_TOKEN("Invalid Token", HttpStatus.BAD_REQUEST),
    EXPIRED_TOKEN("token Expired", HttpStatus.BAD_REQUEST),
    HEADER_NOT_FOUND("Header Not Found", HttpStatus.NOT_FOUND),
    S3_ERROR("S3 작업 과정 중 에러가 발생했습니다.", HttpStatus.BAD_GATEWAY),
    EMAIL_ERROR("Email 전송 과정 중 에러가 발생했습니다.", HttpStatus.BAD_GATEWAY),
    NOT_PERMIT_EMAIL_ERROR("Not Permit Email", HttpStatus.NOT_FOUND),
    CHECK_EMAIL_CODE_ERROR("Error Occurred while Checking Email Code", HttpStatus.NOT_FOUND),
    CHECK_PASSWORD_CODE_ERROR("Error Occurred while Checking Password Code", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("User Not Found", HttpStatus.NOT_FOUND),
    INCORRECT_PASSWORD("Incorrect Password", HttpStatus.BAD_REQUEST),
    NEWS_NOT_FOUND("News Not Found", HttpStatus.NOT_FOUND),
    PROJECT_NOT_FOUND("Project Not Found", HttpStatus.NOT_FOUND),
    ALREADY_SAME_NAME_PROJECT_EXISTS("Already Same Name Project Exists.", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS("User Already Exists", HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER("Null or Invalid Parameter Value Inputted", HttpStatus.BAD_REQUEST),
    CHECK_TEACHER_CODE_ERROR("Error Occurred while Checking Teacher Code", HttpStatus.NOT_FOUND),
    INVALID_COMPANY_CHECK_CODE("Invalid Company Check Code", HttpStatus.BAD_REQUEST)


}