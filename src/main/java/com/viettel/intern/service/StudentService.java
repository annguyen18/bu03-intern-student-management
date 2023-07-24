package com.viettel.intern.service;

import com.viettel.intern.dto.CourseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentService {

    /**
     * Signs up a student for a list of courses.
     *
     * @param studentId The student ID.
     *
     * @param courseIdList The list of course IDs.
     */
    void signUpCourse(Long studentId, List<Long> courseIdList);

    /**
     * Returns a list of all the courses that a student is enrolled in.
     *
     * @param id The student ID.
     * @return A list of CourseDTO objects.
     */
    List<CourseDTO> findEnrollmentsByStudentId(Long id);

    /**
     * Returns a page of courses that are offered by a specific department and that a student is enrolled in.
     *
     * @param id The student ID.
     * @param pageable The pagination information.
     * @return A Page object containing a list of CourseDTO objects and information about the pagination.
     */
    Page<CourseDTO> getCoursesByDepartmentIdByStudentId(Long id, Pageable pageable);

}
