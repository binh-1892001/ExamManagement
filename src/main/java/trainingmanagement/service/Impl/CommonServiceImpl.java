package trainingmanagement.service.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import trainingmanagement.service.CommonService;

import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {
    @Override
    public Page<?> convertListToPages(Pageable pageable, List<?> list) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        return PageableExecutionUtils.getPage(list.subList(start, end), pageable, list::size);
    }
}
