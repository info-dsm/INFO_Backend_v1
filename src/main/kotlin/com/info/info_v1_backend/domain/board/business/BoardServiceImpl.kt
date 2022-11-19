package com.info.info_v1_backend.domain.board.business

import com.info.info_v1_backend.domain.auth.data.repository.user.StudentRepository
import com.info.info_v1_backend.domain.board.business.dto.EmployBoardDto
import com.info.info_v1_backend.domain.board.business.dto.IndicationDto
import com.info.info_v1_backend.domain.board.business.dto.PerClassEmployInfoDto
import com.info.info_v1_backend.domain.board.business.type.ClassInfo
import com.info.info_v1_backend.domain.company.data.entity.company.work.hired.HiredStudent
import com.info.info_v1_backend.global.file.dto.FileResponse
import com.info.info_v1_backend.global.file.entity.type.FileType
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
            student.hiredStudentList.filter {
                !it.isFire || !it.isDelete
            }.map {
                    hiredStudent: HiredStudent ->
                indicationDtoList.add(
                    IndicationDto(
                        hiredStudent.company.id!!,
                        hiredStudent.company.name,
                        (hiredStudent.company.companyIntroduction.companyLogo
                            ?.toFileResponse())
                            ?: FileResponse(
                                0,
                                "https://cdn.pixabay.com/photo/2017/02/13/01/26/the-question-mark-2061539_960_720.png",
                                FileType.DOCS,
                                "png",
                                "NotFound"
                            )
                    )
                )
                employStudentCnt++
            }.isEmpty().let {
                student.fieldTrainingList.filter {
                    !it.isDelete
                }.map {
                    indicationDtoList.add(
                        IndicationDto(
                            it.company.id!!,
                            it.company.name,
                            (it.company.companyIntroduction.companyLogo
                                ?.toFileResponse())
                                ?: FileResponse(
                                    0,
                                    "https://cdn.pixabay.com/photo/2017/02/13/01/26/the-question-mark-2061539_960_720.png",
                                    FileType.DOCS,
                                    "png",
                                    "NotFound"
                                )
                        )
                    )
                    employStudentCnt++
                }
            }
            totalStudentCnt++
        }


        val employRate = if(totalStudentCnt != 0) { employStudentCnt / totalStudentCnt } else { 0 }



        return PerClassEmployInfoDto(
            classInfo,
            classInfo.major,
            employRate,
            indicationDtoList
        )
    }

}