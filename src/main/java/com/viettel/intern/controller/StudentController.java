package com.viettel.intern.controller;

import com.viettel.intern.config.factory.response.GeneralResponse;
import com.viettel.intern.config.factory.response.ResponseFactory;
import com.viettel.intern.dto.CourseDTO;
import com.viettel.intern.service.CourseService;
import com.viettel.intern.service.StudentService;
import com.viettel.intern.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private final ResponseFactory responseFactory;
    private final StudentService studentService;
    private final UserService userService;
    private final CourseService courseService;

    @GetMapping("/courses")
    public ResponseEntity<GeneralResponse<Object>> getCourses(@RequestParam(required = false, defaultValue = "0") int page,
                                                              @RequestParam(required = false, defaultValue = "5") int size,
                                                              @RequestParam(required = false, defaultValue = "name") String sortBy,
                                                              @RequestParam(required = false, defaultValue = "asc") String sortOrder,
                                                              @RequestParam(required = false) String keyword) {
        Long studentId = userService.getCurrentUserId();
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CourseDTO> coursePage = studentService.getCoursesByDepartmentIdByStudentId(studentId, pageable);
        if (keyword != null)
            coursePage = courseService.searchCoursesByNameContaining(keyword, pageable);
        return responseFactory.success(coursePage);
    }

    // client send a json that includes a list of enrollments
    @PostMapping("/enroll")
    public ResponseEntity<GeneralResponse<Object>> signUpCourse(@RequestBody List<Long> signedUpCourseIdList) {
        Long studentId = userService.getCurrentUserId();

        studentService.signUpCourse(studentId, signedUpCourseIdList);

        return responseFactory.successNoData();
    }

    // get all the courses that the student has enrolled in
    @GetMapping("/enrollments")
    public ResponseEntity<GeneralResponse<Object>> getEnrollments() {
        Long id = userService.getCurrentUserId();
        return responseFactory.success(studentService.findEnrollmentsByStudentId(id));
    }

}
