package com.viettel.intern.service.impl;

import com.viettel.intern.config.locale.Translator;
import com.viettel.intern.entity.base.User;
import com.viettel.intern.exception.BusinessException;
import com.viettel.intern.repository.base.UserRepository;
import com.viettel.intern.service.UserService;
import com.viettel.intern.ultis.CommonUtil;
import com.viettel.intern.ultis.EmailUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailUtil emailUtil;
    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void register(User newUser) { // {"username":"admi3n","password":"1233456","email":"admi_n@gmail"}
        if (!CommonUtil.isValidEmail(newUser.getEmail())) {
            throw new BusinessException(Translator.toLocale("email.invalid"));
        }
        if (userRepository.findByEmailIgnoreCase(newUser.getEmail()) != null) {
            throw new BusinessException(Translator.toLocale("email.exists"));
        }
        if (userRepository.findByUsernameIgnoreCase(newUser.getUsername()) != null) {
            throw new BusinessException(Translator.toLocale("username.exists"));
        }
        newUser.setCreatedBy(newUser.getUsername());
        userRepository.save(newUser);
    }
}
