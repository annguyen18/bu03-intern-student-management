package com.viettel.intern.controller;

import com.viettel.intern.config.factory.response.GeneralResponse;
import com.viettel.intern.config.factory.response.ResponseFactory;
import com.viettel.intern.entity.base.Course;
import com.viettel.intern.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/courses")
@AllArgsConstructor
public class CourseController {

    private final ResponseFactory responseFactory;
    private final CourseService courseService;

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("")
    public ResponseEntity<GeneralResponse<Object>> getAllCourses() {
        return responseFactory.success(courseService.getAllCourse());
    }

//     create courses
    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("")
    public ResponseEntity<GeneralResponse<Object>> createCourse(@RequestBody Course course) {
        courseService.save(course);
        return responseFactory.success(course);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("")
    public ResponseEntity<GeneralResponse<Object>> updateCourse(@RequestBody Course course) {
        return responseFactory.success(courseService.update(course));
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse<Object>> removeCourse(@PathVariable Long id) {
        courseService.deleteById(id);
        return responseFactory.successNoData();
    }

}
