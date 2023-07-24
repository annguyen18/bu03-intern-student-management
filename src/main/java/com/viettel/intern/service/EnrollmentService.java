package com.viettel.intern.service;

import com.viettel.intern.entity.base.Enrollment;

import java.util.List;

public interface EnrollmentService {

    /**
     * Saves the specified enrollment to the database
     *
     * @param enrollment The enrollment to be saved
     */
    void save(Enrollment enrollment);

    /**
     * Finds all the enrollments in the database
     *
     * @return a List of all enrollments
     */
    List<Enrollment> getAllEnrollment();

    /**
     * Counts the number of enrollments for the specified course ID.
     *
     * @param id The ID of the course.
     * @return The number of enrollments.
     */
    Integer countEnrollmentsByCourseId(Long id);

    /**
     * Gets the enrollments for the specified student ID.
     *
     * @param id The ID of the student.
     * @return The enrollments.
     */
    List<Enrollment> getEnrollmentsById_StudentId(Long id);

    /**
     * Gets the enrollments for the specified student username.
     *
     * @param username The username of the student.
     * @return The enrollments.
     */
    List<Enrollment> getEnrollmentsByUsername(String username);

    /**
     * Deletes the enrollments for the specified course ID.
     *
     * @param id The ID of the course.
     */
    void deleteEnrollmentsByCourseId(Long id);

    /**
     * Deletes the enrollments for the specified student ID.
     *
     * @param id The ID of the student.
     */
    void deleteEnrollmentsByStudentId(Long id);
}
