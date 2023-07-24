package com.viettel.intern.dto.request;
import com.viettel.intern.entity.base.Authority;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdate {

    private String email;

    private String avatar;

    private Integer status;

    private List<Authority> authorities;
}
