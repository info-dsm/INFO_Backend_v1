package com.info.info_v1_backend.global.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val message: String,
    val status: HttpStatus
) {
    //400
    ALREADY_SAME_NAME_PROJECT_EXISTS("Already Same Name Project Exists.", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS("User Already Exists", HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER("Null or Invalid Parameter Value Inputted", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN("Invalid Token", HttpStatus.BAD_REQUEST),
    EXPIRED_TOKEN("token Expired", HttpStatus.BAD_REQUEST),
    INCORRECT_PASSWORD("Incorrect Password", HttpStatus.BAD_REQUEST),
    EMAIL_ERROR("Email 전송 과정 중 에러가 발생했습니다.", HttpStatus.BAD_GATEWAY),
    INVALID_FILE_EXTENSION("Invalid File Extension", HttpStatus.BAD_REQUEST),
    INVALID_COMPANY_CHECK_CODE("Invalid Company Check Code", HttpStatus.BAD_REQUEST),
    STUDENT_ALREADY_WRITTEN_COMMENT("you have Already Written comment", HttpStatus.BAD_REQUEST),
    CONTACTOR_MUST_LEAVE_LEAST_AT_ONE_ON_COMPANY("Contactor Must leave least at one on company", HttpStatus.BAD_REQUEST),
    IS_NOT_FIELD_TRAINING_STUDENT("Is not fieldtraining student", HttpStatus.BAD_REQUEST),
    IS_ALREADY_FIELD_TRAINING_STUDENT("Is already field training student", HttpStatus.BAD_REQUEST),

    //401
    TOKEN_NOT_FOUND("Token Not Found", HttpStatus.UNAUTHORIZED),
    UN_AUTHORIZED("Un Authorized", HttpStatus.UNAUTHORIZED),

    //403
    NOT_HAVE_ACCESS_TO_THE_PROJECT("Not Have Access To The project", HttpStatus.FORBIDDEN),
    FORBIDDEN("권한이 없습니다", HttpStatus.FORBIDDEN),
    IS_NOT_STUDENT("You are not student", HttpStatus.FORBIDDEN),
    NO_AUTHENTICATION("you have No Authentication", HttpStatus.FORBIDDEN),

    //404
    HEADER_NOT_FOUND("Header Not Found", HttpStatus.NOT_FOUND),
    USERNAME_NOT_FOUND("Username Not Found", HttpStatus.NOT_FOUND),
    NOT_PERMIT_EMAIL_ERROR("Not Permit Email", HttpStatus.NOT_FOUND),
    CHECK_EMAIL_CODE_ERROR("Error Occurred while Checking Email Code", HttpStatus.NOT_FOUND),
    CHECK_PASSWORD_CODE_ERROR("Error Occurred while Checking Password Code", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("User Not Found", HttpStatus.NOT_FOUND),
    NEWS_NOT_FOUND("News Not Found", HttpStatus.NOT_FOUND),
    PROJECT_NOT_FOUND("Project Not Found", HttpStatus.NOT_FOUND),
    CHECK_TEACHER_CODE_ERROR("Error Occurred while Checking Teacher Code", HttpStatus.NOT_FOUND),
    CHECK_CONTACTOR_CODE_ERROR("Error Occurred while Checking Contactor Code", HttpStatus.NOT_FOUND),
    COMPANY_NOT_FOUND("Company Not Found", HttpStatus.NOT_FOUND),
    COMMENT_NOT_FOUND("Comment Not Found", HttpStatus.NOT_FOUND),
    NOTICE_NOT_FOUND("Notice Not Found", HttpStatus.NOT_FOUND),
    CONTACTOR_NOT_FOUND("Contactor Not Found", HttpStatus.NOT_FOUND),
    IS_NOT_CONTACTOR_OR_TEACHER("Is Not Contactor Or Teacher", HttpStatus.NOT_FOUND),
    FILE_NOT_FOUND("File not Found", HttpStatus.NOT_FOUND),
    HIRED_STUDENT_NOT_FOUND("Hired Student Not Found", HttpStatus.NOT_FOUND),
    FIELD_TRAINING_NOT_FOUND("Field training not found", HttpStatus.NOT_FOUND),
    APPLICANT_USER_NOT_FOUND("Applicant user not found", HttpStatus.NOT_FOUND),
    BIG_CLASSIFICATION_NOT_FOUND("Big classification not found", HttpStatus.NOT_FOUND),


    //500
    INTERNAL_SERVER_ERROR("internal server error", HttpStatus.INTERNAL_SERVER_ERROR),

    //502
    S3_ERROR("S3 작업 과정 중 에러가 발생했습니다.", HttpStatus.BAD_GATEWAY),


}