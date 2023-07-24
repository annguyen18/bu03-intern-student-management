package com.viettel.intern.service.impl;

import com.viettel.intern.entity.base.Enrollment;
import com.viettel.intern.repository.base.EnrollmentRepository;
import com.viettel.intern.service.EnrollmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    @Override
    public void save(Enrollment enrollment) {
        enrollmentRepository.save(enrollment);
    }

    @Override
    public List<Enrollment> getAllEnrollment() {
        return null;
    }

    @Override
    public Integer countEnrollmentsByCourseId(Long id) {
        return enrollmentRepository.countEnrollmentsByCourseId(id);
    }

    @Override
    public List<Enrollment> getEnrollmentsById_StudentId(Long id) {
        return enrollmentRepository.getEnrollmentsById_StudentId(id);
    }

    @Override
    public List<Enrollment> getEnrollmentsByUsername(String username) {
        return enrollmentRepository.getEnrollmentsByUsername(username);
    }


    @Override
    public void deleteEnrollmentsByCourseId(Long id) {
        enrollmentRepository.deleteEnrollmentsByCourseId(id);
    }

    @Override
    public void deleteEnrollmentsByStudentId(Long id) {
        enrollmentRepository.deleteEnrollmentsByStudentId(id);
    }

}
