package com.viettel.intern.entity.base;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "DEPARTMENT")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LOCATION")
    private String location;
}
