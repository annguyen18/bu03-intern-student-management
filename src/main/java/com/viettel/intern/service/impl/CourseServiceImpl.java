package com.viettel.intern.service.impl;

import com.viettel.intern.config.locale.Translator;
import com.viettel.intern.dto.CourseDTO;
import com.viettel.intern.entity.base.Course;
import com.viettel.intern.entity.base.Notification;
import com.viettel.intern.repository.base.CourseRepository;
import com.viettel.intern.repository.base.NotificationRepository;
import com.viettel.intern.service.CourseService;
import com.viettel.intern.service.EnrollmentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final EnrollmentService enrollmentService;
    private final NotificationRepository notificationRepository;


    @Override
    public void save(Course course) {
        course.setCreatedBy("ADMIN");
        course.setCreatedDate(new Date());
        courseRepository.save(course);
    }

    @Override
    public Course update(Course course) {
        if (enrollmentService.countEnrollmentsByCourseId(course.getId()) >= 1) {
            addNotification(course, "update");
        }
        return courseRepository.save(course);
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    @Override
    public List<CourseDTO> getAllCourse() {
        return courseRepository.findAll().stream().map(course ->
                CourseDTO.builder()
                        .name(course.getName())
                        .code(course.getCode())
                        .creditCount(course.getCreditCount())
                        .studentCount(enrollmentService.countEnrollmentsByCourseId(course.getId()))
                        .maxStudentCount(course.getMaxStudentCount())
                        .departmentId(course.getDepartmentId())
                        .lecturerId(course.getLecturerId())
                        .scheduleId(course.getScheduleId())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        Course course = this.findById(id);

        if (enrollmentService.countEnrollmentsByCourseId(id) >= 1) {
            enrollmentService.deleteEnrollmentsByCourseId(id);
            addNotification(course, "delete");
        }
        courseRepository.deleteById(id);
    }

    @Scheduled(cron = "${cron.expression}", zone = "${zone}")
    private void autoCancelClass() {
        for (Course course : courseRepository.findAll()){
            if (enrollmentService.countEnrollmentsByCourseId(course.getId()) < 1) {
                course.setStatus(false);
                courseRepository.save(course);
                addNotification(course, "delete");
            }
        }
    }

    @Override
    public Page<CourseDTO> searchCoursesByNameContaining(String keyword, Pageable pageable) {
        return courseRepository.findByNameContainingIgnoreCase(keyword, pageable).
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

    /**
     * Adds a notification to all users who are enrolled in the specified course.
     *
     * @param course The course to add the notification for.
     */
    private void addNotification(Course course, String action) {
        String title = Translator.toLocale("course.title." + action);
        String body = Translator.toLocale("course.body." + action, new String[]{course.getName(), course.getCode()});

        Notification notification = Notification.builder()
                .title(title)
                .body(body)
                .build();
        notificationRepository.save(notification);
    }


}
