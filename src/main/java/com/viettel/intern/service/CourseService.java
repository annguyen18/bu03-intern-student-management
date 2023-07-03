package com.viettel.intern.service;

import com.viettel.intern.entity.base.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    void save(Course course);
    Optional<Course> findById(Long id);
    List<Course> getAllCourse();
    void deleteById(Long id);
//    Boolean existsById(Long id);
//    Department getDepartmentByCourseId(Long id);

}
