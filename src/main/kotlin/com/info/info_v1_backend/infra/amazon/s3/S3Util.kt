package com.info.info_v1_backend.infra.amazon.s3

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.util.IOUtils
import com.info.info_v1_backend.global.file.dto.FileResponse
import com.info.info_v1_backend.global.file.entity.File
import com.info.info_v1_backend.global.file.entity.type.DocsExt
import com.info.info_v1_backend.global.file.entity.type.FileType
import com.info.info_v1_backend.global.file.entity.type.ImageExt
import com.info.info_v1_backend.global.file.exception.InvalidFileExtension
import com.info.info_v1_backend.infra.amazon.s3.env.S3Property
import com.info.info_v1_backend.infra.amazon.s3.exception.S3Exception
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.util.UUID


@Component
class S3Util(
    private val s3Property: S3Property,
    private val s3: AmazonS3Client
) {

    fun uploadFile(file: MultipartFile, rootPathName: String, middlePathName: String): FileResponse {
        val objectMetadata = ObjectMetadata()
        val bytes: ByteArray = IOUtils.toByteArray(file.inputStream)

        objectMetadata.contentLength = bytes.size.toLong()
        val ext = (file.originalFilename?: file.name).substring((file.originalFilename?:file.name).lastIndexOf(".") + 1)

        var fileType: FileType = FileType.IMAGE
        ImageExt.values().filter { it.extension ==  ext }.map {
            objectMetadata.contentType = it.contentType
        }.ifEmpty {
            DocsExt.values().filter { it.extension == ext }.map {
                objectMetadata.contentType = it.contentType
                fileType = FileType.DOCS
            }
        }.ifEmpty {
            throw InvalidFileExtension(ext)
        }


        val byteArrayInputStream = ByteArrayInputStream(bytes)

        val fileName = "${s3Property.bucketName}/${rootPathName}/${middlePathName}/${file.originalFilename}/${UUID.randomUUID()}.$ext"

        try {
            s3.putObject(PutObjectRequest(s3Property.bucketName, fileName, byteArrayInputStream, objectMetadata))
        } catch (err: Exception) {
            throw S3Exception(err.message.toString())
        }
        return FileResponse(
            getFileUrl(fileName),
            fileType,
            ext
        )
    }



    fun getFileUrl(fileName: String): String {
        return s3.getResourceUrl(s3Property.bucketName, fileName)
    }


}