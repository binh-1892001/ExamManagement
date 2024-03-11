package trainingmanagement.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.Wrapper.ResponseWrapper;
import trainingmanagement.model.dto.response.RoleResponse;
import trainingmanagement.model.entity.Enum.EHttpStatus;
import trainingmanagement.service.CommonService;
import trainingmanagement.service.Role.RoleService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/roles")
public class RoleController {
    private final RoleService roleService;
    private final CommonService commonService;
    @GetMapping
    public ResponseEntity<?> getAllRolesToPages(
        @RequestParam(defaultValue = "5", name = "limit") int limit,
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam(defaultValue = "roleName", name = "sort") String sort,
        @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<RoleResponse> roleResponses = roleService.getAllRoleResponsesToList();
            Page<?> roles = commonService.convertListToPages(pageable, roleResponses);
            if (!roles.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                roles.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Roles page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Roles page is out of range.");
        }
    }
    @GetMapping("/search")
    public ResponseEntity<?> getRolesByRoleNameToPages(
        @RequestParam(name = "keyword") String keyword,
        @RequestParam(defaultValue = "5", name = "limit") int limit,
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam(defaultValue = "roleName", name = "sort") String sort,
        @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        Pageable pageable;
        if (order.equals("asc")) pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        else pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        try {
            List<RoleResponse> roleResponses = roleService.findAllByRoleNameContainingIgnoreCase(keyword);
            Page<?> roles = commonService.convertListToPages(pageable, roleResponses);
            if (!roles.isEmpty()) {
                return new ResponseEntity<>(
                        new ResponseWrapper<>(
                                EHttpStatus.SUCCESS,
                                HttpStatus.OK.value(),
                                HttpStatus.OK.name(),
                                roles.getContent()
                        ), HttpStatus.OK);
            }
            throw new CustomException("Roles page is empty.");
        } catch (IllegalArgumentException e) {
            throw new CustomException("Roles page is out of range.");
        }
    }
}
