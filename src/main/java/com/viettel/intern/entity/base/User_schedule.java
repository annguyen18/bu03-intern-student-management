package com.viettel.intern.entity.base;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Mindra Toon
 * @since 6/7/2023 - 14:33 PM
 */
@Entity
@Table(name = "USER_SCHEDULE")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User_schedule extends AbstractAuditingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "class_id")
    private Long class_id;

}
