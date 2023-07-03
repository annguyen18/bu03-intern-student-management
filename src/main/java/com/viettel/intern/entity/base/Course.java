package com.viettel.intern.entity.base;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "COURSE")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CREDIT_COUNT")
    private Integer creditCount;
    @Column(name = "START_DATE")
    private Date startDate;
    @Column(name = "END_DATE")
    private Date endDate;
    @Column(name = "STATUS")
    private Boolean status;
    @Column(name = "CLASSROOM")
    private String classroom;
    @Column(name = "LECTURER_ID")
    private Long lecturerId;
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "DEPARTMENT_ID")
//    private Department department;
//    @ManyToMany
//    @JoinTable(name = "ENROLMENT")
//    private List<Student> students;
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn("LECTURER_ID")
//    private Lecturer lecturer;

}
