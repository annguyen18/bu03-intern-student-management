package com.viettel.intern.service.impl;

import com.viettel.intern.entity.base.Course;
import com.viettel.intern.repository.base.CourseRepository;
import com.viettel.intern.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;


    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

//    @Override
//    public Boolean existsById(Long id) {
//        return null;
//    }
}
