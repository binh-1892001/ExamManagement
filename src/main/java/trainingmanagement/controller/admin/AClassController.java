/**
 * * - Create standard CRUD for ClassService.
 * * - Add both Put and Patch method to edit Class entity.
 * * - Add both softDelete and hardDelete to delete Class entity.
 * * - Sửa lại để có thể lấy cả theo List, Page và ép kiểu theo từng role.
 * * - Sắp xếp lại các Method và Comment để có thể dễ đọc hơn.
 * @ModifyBy: Nguyễn Hồng Quân.
 * @ModifyDate: 25/03/2025.
 * @CreatedBy: Nguyễn Minh Hoàng.
 * @CreatedDate: 13/3/2024.
 * */

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
     * @Param: keyword là từ khoá cần tìm kiếm (không phân biệt hoa thường).
     * @Param: limit là giới hạn bao nhiêu bản ghi mỗi trang hiển thị.
     * @Param: page là trang hiển thị tương ứng.
     * @Param: sort là tên trường dùng để lọc và sắp xếp.
     * @Param: order là asc/desc thể hiện việc sắp xếp ngược hay xuôi.
     * @Return: trả về 1 ResponseEntity đại diện cho Page Class lấy ra được từ Db.
     * */
    @GetMapping
    public ResponseEntity<?> getAllClassesToPages(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "Id", name = "sort") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String order
    ) throws CustomException {
        Page<Classroom> classes = classroomService.getAllClassToPages(limit, page, sort, order);
        Page<AClassResponse> classResponses = classroomService.entityAMap(classes);
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                    HttpStatus.OK.value(),
                    HttpStatus.OK.name(),
                    classResponses.getContent()
            ), HttpStatus.OK);
    }
    /**
     * ? Controller lấy ra 1 đối tượng Class từ Db dành cho Admin.
     * @Param: Long classId của đối tượng Class cần lấy ra.
     * @Return: trả về 1 ResponseEntity đại diện cho Class lấy ra thành côngs.
     * */
    @GetMapping("/{classId}")
    public ResponseEntity<?> getClassById(@PathVariable("classId") Long classId) throws CustomException{
        AClassResponse classroom = classroomService.getAClassById(classId);
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                classroom
            ), HttpStatus.OK);
    }
    /**
     * ? Controller tạo mới và lưu 1 đối tượng Class vào Db dành cho Admin.
     * @Param: AClassRequest dto của Admin dùng để lưu vào trong Db.
     * @Return: trả về 1 ResponseEntity đại diện cho Class đã lưu thành công vào Db.
     * */
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
     * @Param: Long classId của đối tượng Class cần lấy ra.
     * @Param: AClassRequest dto của Admin dùng để lưu vào trong Db.
     * @Return: trả về 1 ResponseEntity đại diện cho Class đã lưu thành công vào Db.
     * */
    @PutMapping("/{classId}")
    public ResponseEntity<?> putUpdateClass(
            @PathVariable("classId") Long updateClassId,
            @RequestBody @Valid AClassRequest classRequest
    ) throws CustomException {
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        EHttpStatus.SUCCESS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        classroomService.putUpdateClass(updateClassId, classRequest)
                ), HttpStatus.OK);
    }
    /**
     * ? Controller sửa đổi thông tin của 1 đối tượng Class trong Db (nếu có) dành cho Admin.
     * @Param: Long classId của đối tượng Class cần lấy ra.
     * @Param: AClassRequest dto của Admin dùng để lưu vào trong Db.
     * @Return: trả về 1 ResponseEntity đại diện cho Class đã lưu thành công vào Db.
     * */
    @PatchMapping("/{classId}")
    public ResponseEntity<?> patchUpdateClass(
            @PathVariable("classId") Long updateClassId,
            @RequestBody @Valid AClassRequest classRequest
    ) throws CustomException {
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                classroomService.patchUpdateClass(updateClassId, classRequest)
            ), HttpStatus.OK);
    }
    /**
     * ? Controller xoá mềm 1 đối tượng Class trong Db (nếu có) dành cho Admin.
     * @Param: Long classId của đối tượng Class cần lấy ra.
     * @Return: trả về 1 ResponseEntity thông báo đã xoá thành công.
     * */
    @DeleteMapping("{classId}")
    public ResponseEntity<?> softDeleteClassById(@PathVariable("classId") Long classId) throws CustomException {
        classroomService.softDeleteByClassId(classId);
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Delete class successfully."
            ), HttpStatus.OK);
    }
    /**
     * ? Controller xoá cứng 1 đối tượng Class trong Db (nếu có) dành cho Admin.
     * @Param: Long classId của đối tượng Class cần lấy ra.
     * @Return: trả về 1 ResponseEntity thông báo đã xoá thành công.
     * */
    @DeleteMapping("/delete/{classId}")
    public ResponseEntity<?> hardDeleteClassById(@PathVariable("classId") Long classId) throws CustomException {
        classroomService.hardDeleteByClassId(classId);
        return new ResponseEntity<>(
            new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Delete class successfully."
            ), HttpStatus.OK);
    }
    /**
     * ? Controller tìm kiếm và lấy ra 1 Page đối tượng Class từ Db dành cho Admin.
     * @Param: keyword là từ khoá cần tìm kiếm (không phân biệt hoa thường).
     * @Param: limit là giới hạn bao nhiêu bản ghi mỗi trang hiển thị.
     * @Param: page là trang hiển thị tương ứng.
     * @Param: sort là tên trường dùng để lọc và sắp xếp.
     * @Param: order là asc/desc thể hiện việc sắp xếp ngược hay xuôi.
     * @Return: trả về 1 ResponseEntity đại diện cho Page Class lấy ra được từ Db.
     * */
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