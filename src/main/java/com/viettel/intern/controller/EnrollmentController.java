package com.viettel.intern.controller;

import com.viettel.intern.config.factory.response.GeneralResponse;
import com.viettel.intern.config.factory.response.ResponseFactory;
import com.viettel.intern.entity.base.Enrollment;
import com.viettel.intern.service.CourseService;
import com.viettel.intern.service.EnrollmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin/enrollments")
public class EnrollmentController {

    private final ResponseFactory responseFactory;
    private final EnrollmentService enrollmentService;
    private final CourseService courseService;

    @GetMapping("")
    public ResponseEntity<GeneralResponse<Object>> getEnrolledCoursesByStudentId(@RequestParam(required = false) Long id,
                                                                                 @RequestParam(required = false) String username) {
        List<Enrollment> studentCourseList;
        if (username != null)
            studentCourseList = enrollmentService.getEnrollmentsByUsername(username);

        else
            studentCourseList = enrollmentService.getEnrollmentsById_StudentId(id);

        return responseFactory.success(studentCourseList.stream()
                .map(enrollment -> enrollment.getId().getCourseId())
                .filter(Objects::nonNull) // Filter out null courses
                .collect(Collectors.toList()));
    }

}
