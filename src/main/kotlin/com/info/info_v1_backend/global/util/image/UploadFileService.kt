package com.info.info_v1_backend.global.util.image

import com.info.info_v1_backend.domain.auth.data.entity.user.User
import org.springframework.web.multipart.MultipartFile

interface UploadFileService {

    fun uploadImageLogo(image: MultipartFile, target: UploadFile): UploadFile

    fun uploadImageList(imageList: List<MultipartFile>, target: UploadFile): UploadFile
    fun removeImage(user: User, imageUrl: String, target: UploadFile): UploadFile

}