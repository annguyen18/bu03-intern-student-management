package com.viettel.intern.entity.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "COURSE")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course extends AbstractAuditingEntity implements Serializable {
    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CREDIT_COUNT")
    private Integer creditCount;
    @Column(name = "CODE")
    private String code;
    @Column(name = "MAX_STUDENT_COUNT")
    private Integer maxStudentCount;
    @Column(name = "STATUS")
    private Boolean status;
    @Column(name = "CLASSROOM")
    private String classroom;
    @Column(name = "LECTURER_ID")
    private Long lecturerId;
    @Column(name = "SCHEDULE_ID")
    private Long scheduleId;
    @Column(name = "DEPARTMENT_ID")
    private Long departmentId;


}
