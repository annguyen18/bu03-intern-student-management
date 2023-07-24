package com.viettel.intern.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private String name;
    private String code;
    private Integer creditCount;
    private Integer studentCount;
    private Integer maxStudentCount;
    private Long lecturerId;
    private Long departmentId;
    private Long scheduleId;
}
