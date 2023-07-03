package com.viettel.intern.controller;

import com.viettel.intern.config.factory.response.GeneralResponse;
import com.viettel.intern.config.factory.response.ResponseFactory;
import com.viettel.intern.entity.base.Course;
import com.viettel.intern.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
@AllArgsConstructor
public class CourseController {

    private final ResponseFactory responseFactory;
    private final CourseService courseService;

    @GetMapping("")
    public ResponseEntity<GeneralResponse<Object>> getAllCourses() {
        return responseFactory.success(courseService.getAllCourse());
    }

    @PostMapping("")
    public ResponseEntity<GeneralResponse<Object>> createCourse(@RequestBody Course course) {
        courseService.save(course);
        return responseFactory.success(course);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse<Object>> removeCourse(@PathVariable Long id) {
        courseService.deleteById(id);
        return responseFactory.successNoData();
    }

}
