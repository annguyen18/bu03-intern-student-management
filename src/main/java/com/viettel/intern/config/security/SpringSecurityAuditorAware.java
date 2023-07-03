package com.viettel.intern.config.security;

import com.viettel.intern.ultis.SecurityUtil;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return SecurityUtil.getCurrentUserLogin();
    }
}
