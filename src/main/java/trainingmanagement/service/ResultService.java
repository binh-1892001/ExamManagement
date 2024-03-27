package trainingmanagement.service;

import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.student.ListStudentChoice;
import trainingmanagement.model.dto.request.teacher.TResultRequest;
import trainingmanagement.model.dto.response.teacher.TResultResponse;
import trainingmanagement.model.entity.Result;
import trainingmanagement.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface ResultService {
    //* Check cau tra loi cua sinh vien va may cham diem
    Result checkAndResultTest(ListStudentChoice listStudentChoice, Long testId) throws CustomException;
    //* học sinh xem lịch sử điểm các bài đã làm
    List<Result> getAllByStudent();

    // <----- ROLE TEACHER BEGIN
    List<Result> getAllToList(User teacher);
    List<TResultResponse> getAllResultResponsesToList(User teacher);
    Optional<Result> getById(Long id);
    Result save(Result result);
    Result save(TResultRequest resultRequest) throws CustomException;
    Result patchUpdateResult(Long resultId, TResultRequest resultRequest) throws CustomException;
    void hardDeleteById(Long id);
    void softDeleteById(Long id) throws CustomException;
    List<TResultResponse> searchByStudentFullName(String fullName);
    TResultResponse entityTMap(Result result);
    Result entityTMap(TResultRequest tResultRequest);
    // END ------>
}
