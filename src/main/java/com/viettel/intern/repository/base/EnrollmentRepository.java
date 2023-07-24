package com.viettel.intern.repository.base;

import com.viettel.intern.entity.base.Enrollment;
import com.viettel.intern.entity.base.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {

    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.id.courseId = :courseId")
    Integer countEnrollmentsByCourseId(@Param("courseId") Long id);

    List<Enrollment> getEnrollmentsById_StudentId(Long id);

    @Query("SELECT e FROM Enrollment e JOIN User u ON e.id.studentId = u.id WHERE u.username = :username")
    List<Enrollment> getEnrollmentsByUsername(String username);
    @Modifying
    @Query("DELETE FROM Enrollment e WHERE e.id.courseId = :courseId ")
    void deleteEnrollmentsByCourseId(@Param("courseId") Long courseId);

    @Modifying
    @Query("DELETE FROM Enrollment e WHERE e.id.studentId = :studentId")
    void deleteEnrollmentsByStudentId(@Param("studentId") Long studentId);
}
