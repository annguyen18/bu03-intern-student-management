package com.viettel.intern.entity.base;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity


public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "STUDENT-ID")
    private Long studentId;

    @Column(name = "COURSE_ID")
    private Long courseId;

}
