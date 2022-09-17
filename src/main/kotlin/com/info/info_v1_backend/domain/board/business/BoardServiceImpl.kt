package com.info.info_v1_backend.domain.board.business

import com.info.info_v1_backend.domain.auth.data.repository.user.StudentRepository
import com.info.info_v1_backend.domain.board.business.dto.EmployBoardDto
import com.info.info_v1_backend.domain.board.business.dto.IndicationDto
import com.info.info_v1_backend.domain.board.business.dto.PerClassEmployInfoDto
import com.info.info_v1_backend.domain.board.business.type.ClassInfo
import com.info.info_v1_backend.infra.amazon.s3.dto.ImageDto
import org.springframework.stereotype.Service

@Service
class BoardServiceImpl(
    private val studentRepository: StudentRepository,
): BoardService {

    private var totalRate = 0

    override fun getFullBoard(): EmployBoardDto {

        return EmployBoardDto(
            totalRate,
            listOf(
                getPerClassInfoBoard(ClassInfo.FIRST),
                getPerClassInfoBoard(ClassInfo.SECOND),
                getPerClassInfoBoard(ClassInfo.THIRD),
                getPerClassInfoBoard(ClassInfo.FOURTH),
            )
        )
    }

    private fun getPerClassInfoBoard(classInfo: ClassInfo): PerClassEmployInfoDto {
        val indicationDtoList: MutableList<IndicationDto> = ArrayList()
        var totalStudentCnt = 0
        var employStudentCnt = 0

        studentRepository.findAllByStudentKeyStartingWith("3${classInfo.classNum}").map { student ->
            student.company?.let { company ->
                indicationDtoList.add(
                    IndicationDto(
                        company.id!!,
                        company.shortName,
                        try {

                            ImageDto(
                                company.photoList.first().let {
                                    it.fileUrl
                                },
                                company.id!!
                                )
                        } catch (e: NoSuchElementException) {
                            ImageDto(
                                "NULL",
                                0
                            )
                        }

                    )
                )
                employStudentCnt++
            }
            totalStudentCnt++
        }

        val employRate = employStudentCnt/totalStudentCnt



        return PerClassEmployInfoDto(
            classInfo,
            classInfo.major,
            employRate,
            indicationDtoList
        )
    }

}