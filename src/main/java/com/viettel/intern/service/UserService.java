package com.viettel.intern.service;

import com.viettel.intern.dto.request.UserRegister;
import com.viettel.intern.dto.request.UserUpdate;
import com.viettel.intern.dto.UserDTO;
import com.viettel.intern.entity.base.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser();

    /**
     * Phương thức register sẽ tạo ra một user mới với các thông tin được truyền vào
     *
     * @param userRegister : username, password, email, authorities
     */
    void register(UserRegister userRegister);

    /**
     * Phương thức update sẽ cập nhật thông tin của user hiện tại
     *
     * @param userUpdate : email, avatar, authorities, status
     *
     */
    void update(UserUpdate userUpdate);

    /**
     * Phương thức delete sẽ xóa user có username truyền vào
     *
     * @param username : username của user cần xóa
     */
    void delete(String username);

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
