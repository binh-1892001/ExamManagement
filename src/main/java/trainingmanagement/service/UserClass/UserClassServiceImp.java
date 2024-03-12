package trainingmanagement.service.UserClass;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trainingmanagement.model.entity.UserClass;
import trainingmanagement.repository.UserClassRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserClassServiceImp implements UserClassService{
    private final UserClassRepository userClassRepository;

    @Override
    public UserClass addStudent(UserClass userClass) {
        return null;
    }

    @Override
    public UserClass addTeacher(UserClass userClass) {
        return null;
    }
}
