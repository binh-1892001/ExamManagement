package trainingmanagement.service;

import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.student.ListStudentChoice;
import trainingmanagement.model.entity.Result;

public interface ResultService {
    //* Check cau tra loi cua sinh vien va may cham diem
    Result checkAndResultTest(ListStudentChoice listStudentChoice, Long testId) throws CustomException;
}
