package com.viettel.intern.entity.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "class_schedule")
public class ClassSchedule {
    @Id
    private long id;
    private String dayOfDate;
    private Date startTime;
    private Date endTime;

}
