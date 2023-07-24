package com.viettel.intern.repository.base;

import com.viettel.intern.entity.base.Course;
import com.viettel.intern.entity.base.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<User, Long> {

    // get all the courses that a student has enrolled in :)))
    @Query("SELECT c FROM Course c JOIN Enrollment e ON c.id = e.id.courseId WHERE e.id.studentId = :studentId")
    List<Course> findEnrollmentsByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT c FROM Course c " +
            "JOIN Department d ON c.departmentId = d.id " +
            "JOIN User s on s.departmentId = d.id " +
            "WHERE s.id = :studentId")
    Page<Course> getCoursesByDepartmentIdByStudentId(@Param("studentId") Long id, Pageable pageable);
}
