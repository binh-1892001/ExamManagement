/**
 * * - Create standard CRUD for ClassService.
 * * - Add both Put and Patch method to edit Class entity.
 * * - Add both softDelete and hardDelete to delete Class entity.
 * * - Sửa lại để có thể lấy cả theo List, Page và ép kiểu theo từng role.
 * * - Sắp xếp lại các Method và Comment để có thể dễ đọc hơn.
 *
 * @ModifyBy: Nguyễn Hồng Quân.
 * @ModifyDate: 25/03/2025.
 * @CreatedBy: Nguyễn Minh Hoàng.
 * @CreatedDate: 13/3/2024.
 */

package trainingmanagement.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trainingmanagement.exception.CustomException;
import trainingmanagement.model.dto.request.admin.AClassRequest;
import trainingmanagement.model.dto.wrapper.ResponseWrapper;
import trainingmanagement.model.dto.response.admin.AClassResponse;
import trainingmanagement.model.entity.Classroom;
import trainingmanagement.model.enums.EHttpStatus;
import trainingmanagement.service.ClassroomService;

@RestController
@RequestMapping("/v1/admin/classes")
@RequiredArgsConstructor
public class AClassController {
    private final ClassroomService classroomService;

    /**
     * ? Controller lấy ra 1 Page đối tượng Class từ Db dành cho Admin.
     *
     * @param limit là giới hạn bao nhiêu bản ghi mỗi trang hiển thị.
     * @param page  là trang hiển thị tương ứng.
     * @param sort  là tên trường dùng để lọc và sắp xếp.
     * @param order là asc/desc thể hiện việc sắp xếp ngược hay xuôi.
     * @return trả về 1 ResponseEntity đại diện cho Page Class lấy ra được từ Db.
     */
    @GetMapping
    public ResponseEntity<?> getAllClassesToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "Id", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        try {
            Page<Classroom> classes = classroomService.getAllClassToPages(limit, page, sort, order);
            Page<AClassResponse> classResponses = classroomService.entityAMap(classes);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            classResponses.getContent()
                    ), HttpStatus.OK);
        } catch (Exception exception) {
            throw new CustomException("An error occurred while processing the query!");
        }
    }

    /**
     * ? Controller lấy ra 1 đối tượng Class từ Db dành cho Admin.
     *
     * @param classId của đối tượng Class cần lấy ra.
     * @return trả về 1 ResponseEntity đại diện cho Class lấy ra thành công.
     */
    @GetMapping("/{classId}")
    public ResponseEntity<?> getClassById(@PathVariable("classId") String classId) throws CustomException {
        try {
            Long id = Long.parseLong(classId);
            AClassResponse classroom = classroomService.getAClassById(id);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            classroom
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    /**
     * ? Controller tạo mới và lưu 1 đối tượng Class vào Db dành cho Admin.
     *
     * @param classRequest dto của Admin dùng để lưu vào trong Db.
     * @return trả về 1 ResponseEntity đại diện cho Class đã lưu thành công vào Db.
     */
    @PostMapping
    public ResponseEntity<?> createClass(@RequestBody @Valid AClassRequest classRequest) throws CustomException {
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.name(),
                        classroomService.createClass(classRequest)
                ), HttpStatus.CREATED);
    }

    /**
     * ? Controller sửa đổi thông tin của 1 đối tượng Class trong Db (nếu có) dành cho Admin, nếu không thì thêm mới.
     *
     * @param updateClassId của đối tượng Class cần lấy ra.
     * @param classRequest  dto của Admin dùng để lưu vào trong Db.
     * @return trả về 1 ResponseEntity đại diện cho Class đã lưu thành công vào Db.
     */
    @PutMapping("/{classId}")
    public ResponseEntity<?> putUpdateClass(
            @PathVariable("classId") String updateClassId,
            @RequestBody @Valid AClassRequest classRequest
    ) throws CustomException {
        try {
            Long id = Long.parseLong(updateClassId);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            classroomService.putUpdateClass(id, classRequest)
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    /**
     * ? Controller sửa đổi thông tin của 1 đối tượng Class trong Db (nếu có) dành cho Admin.
     *
     * @param updateClassId classId của đối tượng Class cần lấy ra.
     * @param classRequest  dto của Admin dùng để lưu vào trong Db.
     * @return trả về 1 ResponseEntity đại diện cho Class đã lưu thành công vào Db.
     */
    @PatchMapping("/{classId}")
    public ResponseEntity<?> patchUpdateClass(
            @PathVariable("classId") String updateClassId,
            @RequestBody @Valid AClassRequest classRequest
    ) throws CustomException {
        try {
            Long id = Long.parseLong(updateClassId);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            classroomService.patchUpdateClass(id, classRequest)
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    /**
     * ? Controller xoá mềm 1 đối tượng Class trong Db (nếu có) dành cho Admin.
     *
     * @param classId của đối tượng Class cần lấy ra.
     * @return trả về 1 ResponseEntity thông báo đã xoá thành công.
     */
    @DeleteMapping("{classId}")
    public ResponseEntity<?> softDeleteClassById(@PathVariable("classId") String classId) throws CustomException {
        try {
            Long id = Long.parseLong(classId);
            classroomService.softDeleteByClassId(id);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            "Delete class successfully."
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    /**
     * ? Controller xoá cứng 1 đối tượng Class trong Db (nếu có) dành cho Admin.
     *
     * @param classId của đối tượng Class cần lấy ra.
     * @return trả về 1 ResponseEntity thông báo đã xoá thành công.
     */
    @DeleteMapping("/delete/{classId}")
    public ResponseEntity<?> hardDeleteClassById(@PathVariable("classId") String classId) throws CustomException {
        try {
            Long id = Long.parseLong(classId);
            classroomService.hardDeleteByClassId(id);
            return new ResponseEntity<>(
                    new ResponseWrapper<>(
                            EHttpStatus.SUCCESS,
                            HttpStatus.OK.value(),
                            HttpStatus.OK.name(),
                            "Delete class successfully."
                    ), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new CustomException("Incorrect id number format");
        }
    }

    /**
     * ? Controller tìm kiếm và lấy ra 1 Page đối tượng Class từ Db dành cho Admin.
     *
     * @param keyword là từ khoá cần tìm kiếm (không phân biệt hoa thường).
     * @param limit   là giới hạn bao nhiêu bản ghi mỗi trang hiển thị.
     * @param page    là trang hiển thị tương ứng.
     * @param sort    là tên trường dùng để lọc và sắp xếp.
     * @param order   là asc/desc thể hiện việc sắp xếp ngược hay xuôi.
     * @return trả về 1 ResponseEntity đại diện cho Page Class lấy ra được từ Db.
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "className", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        Page<Classroom> classes = classroomService.searchAllClassByClassNameToPages(keyword, limit, page, sort, order);
        Page<AClassResponse> classResponses = classroomService.entityAMap(classes);
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        classResponses.getContent()
                ), HttpStatus.OK);
    }
}