package trainingmanagement.service;

import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.student.ListStudentChoice;
import trainingmanagement.model.entity.Result;
import trainingmanagement.model.entity.User;

import java.util.List;

public interface ResultService {
    //* Check cau tra loi cua sinh vien va may cham diem
    Result checkAndResultTest(ListStudentChoice listStudentChoice, Long testId) throws CustomException;

    //* học sinh xem lịch sử điểm các bài đã làm
    List<Result> getAllByStudent();
}
