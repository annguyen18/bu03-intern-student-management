package com.viettel.intern.service.impl;

import com.viettel.intern.dto.CourseDTO;
import com.viettel.intern.entity.base.Course;
import com.viettel.intern.entity.base.Enrollment;
import com.viettel.intern.entity.base.EnrollmentId;
import com.viettel.intern.entity.base.User;
import com.viettel.intern.repository.base.StudentRepository;
import com.viettel.intern.service.CourseService;
import com.viettel.intern.service.EnrollmentService;
import com.viettel.intern.service.StudentService;
import com.viettel.intern.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;
    private final UserService userService;
    private final EnrollmentService enrollmentService;
    private final CourseService courseService;

    @Override
    public void signUpCourse(Long studentId, List<Long> courseIdList) {

        // delete all previous enrollments of that student
        enrollmentService.deleteEnrollmentsByStudentId(studentId);
        for (Long courseId : courseIdList) {
            Course course = courseService.findById(courseId);
            User student = userService.findById(studentId);

            // prevent students from signing courses outside their department
            if (!Objects.equals(course.getDepartmentId(), student.getDepartmentId()))
                continue;

            // if the course has reached max student count then student cannot enroll anymore
            if (Objects.equals(enrollmentService.countEnrollmentsByCourseId(courseId),
                    course.getMaxStudentCount()))
                continue;

            enrollmentService.save(new Enrollment(new EnrollmentId(studentId, courseId)));
        }
    }

    @Override
    public List<CourseDTO> findEnrollmentsByStudentId(Long id) {
        return repository.findEnrollmentsByStudentId(id).stream()
                .map(item -> CourseDTO.builder()
                        .name(item.getName())
                        .code(item.getCode())
                        .studentCount(enrollmentService.countEnrollmentsByCourseId(item.getId()))
                        .maxStudentCount(item.getMaxStudentCount())
                        .creditCount(item.getCreditCount())
                        .lecturerId(item.getLecturerId())
                        .scheduleId(item.getScheduleId())
                        .build())
                        .collect(Collectors.toList());
    }

    @Override
    public Page<CourseDTO> getCoursesByDepartmentIdByStudentId(Long id, Pageable pageable) {
                return repository.getCoursesByDepartmentIdByStudentId(id, pageable).
                map(item -> {CourseDTO dto = new CourseDTO();
                    dto.setName(item.getName());
                    dto.setCode(item.getCode());
                    dto.setStudentCount(enrollmentService.countEnrollmentsByCourseId(item.getId()));
                    dto.setMaxStudentCount(item.getMaxStudentCount());
                    dto.setCreditCount(item.getCreditCount());
                    dto.setLecturerId(item.getLecturerId());
                    dto.setScheduleId(item.getScheduleId());
                    return dto;
                });
    }

}
