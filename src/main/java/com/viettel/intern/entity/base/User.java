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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "USER")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractAuditingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @JsonIgnore
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "AVATAR")
    private String avatar;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "DEPARTMENT_ID")
    private Long departmentId;

    @ManyToMany
    @JoinTable(
        name = "user_authority",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "authority_id"))
    @Column(name = "AUTHORITIES")
    private List<Authority> authorities;
}
