package com.info.info_v1_backend.global.util.image

import com.info.info_v1_backend.domain.auth.data.entity.user.User
import com.info.info_v1_backend.domain.project.business.service.RegisteredProjectServiceImpl
import com.info.info_v1_backend.domain.project.data.entity.Image
import com.sun.imageio.plugins.common.ImageUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile


@Service
class UploadFileServiceImpl(
    private val imageUtil: ImageUtil
): UploadFileService {

    @Transactional(propagation = Propagation.NESTED)
    override fun uploadImageLogo(image: MultipartFile, target: UploadFile): UploadFile {
        target.addLogoImg(
            Image(
                image.originalFilename?:"UNTITLED",
                imageUtil.uploadFile(image, RegisteredProjectServiceImpl.IMAGE_ROOT_NAME, target.getIdentity())
            )

        )

        return target
    }


    @Transactional(propagation = Propagation.NESTED)
    override fun uploadImageList(imageList: List<MultipartFile>, target: UploadFile): UploadFile {
        val parsedImageList: MutableList<Image> = ArrayList()
        imageList.map {
            parsedImageList.add(
                Image(
                    it.originalFilename?:"UNTITLED",
                    imageUtil.uploadFile(it, RegisteredProjectServiceImpl.IMAGE_ROOT_NAME, target.getIdentity())
                )
            )
        }

        parsedImageList.map {
            target.addImg(it)
        }

        return target
    }


    @Transactional(propagation = Propagation.NESTED)
    override fun removeImage(user: User, imageUrl: String, target: UploadFile): UploadFile {
        for (img in target.getImgList()) {
            if (img.url == imageUrl) {
                target.removeImg(img)
            }
        }
        return target
    }
}