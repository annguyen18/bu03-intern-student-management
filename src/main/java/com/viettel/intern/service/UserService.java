package com.viettel.intern.service;

import com.viettel.intern.dto.UserDTO;
import com.viettel.intern.entity.base.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser();

    void register(User newUser);

    /**
     * Lấy thông tin cá nhân tài khoản có tên userName
     * @param userName
     * @return Thông tin cá nhân dạng UserDTO
     */
    UserDTO getProfile(String userName);

    /**
     * Thay đổi thông tin cá nhân của userName bằng userDTO
     * @param userName
     * @param userDTO
     */
    void updateProfile(String userName, UserDTO userDTO);
}
