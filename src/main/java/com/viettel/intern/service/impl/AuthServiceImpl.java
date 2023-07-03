package com.viettel.intern.service.impl;

import com.viettel.intern.config.locale.Translator;
import com.viettel.intern.dto.EmailObjectRequest;
import com.viettel.intern.dto.request.ForgotPasswordRequest;
import com.viettel.intern.entity.base.User;
import com.viettel.intern.exception.BusinessException;
import com.viettel.intern.repository.base.UserRepository;
import com.viettel.intern.service.AuthService;
import com.viettel.intern.ultis.CommonUtil;
import com.viettel.intern.ultis.EmailUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final EmailUtil emailUtil;
    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder passwordEncoder;
    private static final String RESET_PW_PREFIX = "reset_pw_";

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        if (!CommonUtil.isValidEmail(request.getEmail())) {
            throw new BusinessException(Translator.toLocale("email.invalid"));
        }

        User user = userRepository.findByEmailIgnoreCase(request.getEmail());
        if (user == null) {
            throw new BusinessException(Translator.toLocale("email.not-exists"));
        }
        String uuid = UUID.randomUUID().toString();
        String email = user.getEmail().trim().toLowerCase();

        // Xóa các key cũ
        String keyGetUUID = RESET_PW_PREFIX + email;
        String oldUUID = redisTemplate.opsForValue().get(keyGetUUID);
        if (oldUUID != null) {
            redisTemplate.delete(RESET_PW_PREFIX + oldUUID);
        }

        // Lưu các key mới
        redisTemplate.opsForValue().set(keyGetUUID, uuid, 30, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(RESET_PW_PREFIX + uuid, email, 30, TimeUnit.MINUTES);

        String url = "http://localhost:8080/api/v1/auth/reset-password?key=" + uuid;

        Map<String, Object> params = Map.of("username", user.getUsername(),
                "url", url);

        EmailObjectRequest emailRequest = EmailObjectRequest.builder()
                .emailTo(new String[]{user.getEmail()})
                .subject(Translator.toLocale("mail.auth.title-reset-password"))
                .template("email-reset-password")
                .params(params)
                .build();

        emailUtil.sendEmail(emailRequest);
    }

    @Transactional
    @Override
    public void resetPassword(String key) {
        String keyGetEmail = RESET_PW_PREFIX + key;
        String email = redisTemplate.opsForValue().get(keyGetEmail);
        if (email == null) {
            throw new BusinessException(Translator.toLocale("mail.url-invalid"));
        }
        String keyGetUUID = RESET_PW_PREFIX + email;
        String uuid = redisTemplate.opsForValue().get(keyGetUUID);
        if (uuid != null) {
            redisTemplate.delete(keyGetEmail);
            redisTemplate.delete(keyGetUUID);
        }

        User user = userRepository.findByEmailIgnoreCase(email);
        String newPassword = RandomStringUtils.randomAlphanumeric(10);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        Map<String, Object> params = Map.of("username", user.getUsername(),
                "newPassword", newPassword);

        EmailObjectRequest emailRequest = EmailObjectRequest.builder()
                .emailTo(new String[]{user.getEmail()})
                .subject(Translator.toLocale("email-new-password"))
                .template("email-new-password")
                .params(params)
                .build();

        emailUtil.sendEmail(emailRequest);
    }
}
