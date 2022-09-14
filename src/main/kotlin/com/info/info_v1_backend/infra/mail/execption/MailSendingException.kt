package com.info.info_v1_backend.infra.mail.execption

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class MailSendingException(data: String): GlobalError(ErrorCode.EMAIL_ERROR, data) {
}