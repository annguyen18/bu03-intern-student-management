package com.viettel.intern.config.security;

import com.viettel.intern.constant.AppConstant;
import com.viettel.intern.entity.base.User;
import com.viettel.intern.repository.base.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component("userDetailsService")
@Slf4j
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameIgnoreCase(username);
        if (user != null) {
            List<GrantedAuthority> grantedAuthorityList = user.getAuthorities()
                    .stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                    .collect(Collectors.toList());

            return new CustomUser(user.getUsername(), user.getPassword(), Objects.equals(AppConstant.ActiveStatus.ACTIVE, user.getStatus()),
                    true, true, true, grantedAuthorityList, user.getId(), user.getEmail(), user.getStatus());
        }
        throw new UsernameNotFoundException("User không tồn tại");
    }
}
