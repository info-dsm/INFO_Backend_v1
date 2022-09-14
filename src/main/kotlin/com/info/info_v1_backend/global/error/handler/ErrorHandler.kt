package com.info.info_v1_backend.global.error.handler

import com.amazonaws.Response
import com.info.info_v1_backend.global.error.data.ErrorResponse
import com.info.info_v1_backend.global.error.data.GlobalError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ErrorHandler {


    @ExceptionHandler(GlobalError::class)
    fun globalExceptionHandler(error: GlobalError): ResponseEntity<*> {
        return ResponseEntity.status(error.errorCode.status).body(
            ErrorResponse(
                error.errorCode.message,
                error.data
            )
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse(
                e.bindingResult.allErrors[0].defaultMessage.toString(),
                e.bindingResult.allErrors[0].arguments?.get(0).toString()
            )
        )
    }

}