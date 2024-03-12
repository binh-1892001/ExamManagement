package trainingmanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CommonService {
    Page<?> convertListToPages(Pageable pageable, List<?> list);
}