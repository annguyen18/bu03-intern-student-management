package com.viettel.intern.config.security;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CustomUser extends User {
    private Long userId;
    private String email;
    private Integer status;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                      boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    @SuppressWarnings({"java:S107"})
    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                      boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Long userId, String email, Integer status) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.email = email;
        this.status = status;
    }
}
