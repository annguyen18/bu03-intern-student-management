package com.viettel.intern.service.impl;

import com.viettel.intern.entity.base.User;
import com.viettel.intern.repository.base.UserRepository;
import com.viettel.intern.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

}
