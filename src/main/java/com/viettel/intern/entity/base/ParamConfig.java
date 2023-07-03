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

/**
 * @author KhaiBQ
 * @since 6/8/2023 - 9:33 PM
 */
@Entity
@Table(name = "PARAM_CONFIG")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParamConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PARAM_KEY")
    private String key;

    @Column(name = "PARAM_VALUE")
    private String value;

    @Column(name = "ENV")
    private String env;

    @Column(name = "DESCRIPTION")
    private String description;
}
