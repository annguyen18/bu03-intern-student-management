package com.viettel.intern.service;

import com.viettel.intern.dto.CourseDTO;
import com.viettel.intern.entity.base.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {
    /**
     * Saves the specified course to the database
     *
     * @param course The course to be saved
     */
    void save(Course course);


    /**
     * Updates the specified course in the database.
     *
     * @param course The course to be updated
     * @return The updated course
     */
    Course update(Course course);


    /**
     * Finds the course with the specified ID.
     *
     * @param id The ID of the course to find.
     * @return The course with the specified ID, or null if the course does not exist.
     */
    Course findById(Long id);

    /**
     * Finds all the courses in the database
     *
     * @return a List of all courses
     */
    List<CourseDTO> getAllCourse();

    /**
     * Deleted the specified course in the database
     *
     * @param id The ID of the course to delete
     */
    void deleteById(Long id);

    /**
     * Searches for courses by name containing the specified keyword.
     *
     * @param keyword The keyword to search for.
     * @param pageable The pagination information.
     * @return A page of courses matching the search criteria.
     */
    Page<CourseDTO> searchCoursesByNameContaining(String keyword, Pageable pageable);
}
