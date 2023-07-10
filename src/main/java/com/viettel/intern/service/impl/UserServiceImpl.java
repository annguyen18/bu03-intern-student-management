package com.viettel.intern.service.impl;

import com.viettel.intern.config.locale.Translator;
import com.viettel.intern.dto.UserDTO;
import com.viettel.intern.entity.base.User;
import com.viettel.intern.exception.BusinessException;
import com.viettel.intern.repository.base.UserRepository;
import com.viettel.intern.service.UserService;
import com.viettel.intern.ultis.CommonUtil;
import com.viettel.intern.ultis.EmailUtil;
import com.viettel.intern.ultis.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
    }

    /**
     * Tra ve thông tin cá nhân tài khoản có tên userName dạng DTO
     *
     * @param userName : tên tài khoản cần ấy thông tin
     * @return userDTO
     * @throws BusinessException Ném ra khi không tìm thấy người dùng với userName
     *                           cung cấp hoặc không có quyền truy cập.
     */
    @Override
    public UserDTO getProfile(String userName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String account = authentication.getName();
//      Kiểm tra tài khoản đăng nhập có quyền admin hoặc có trùng tên tài khoản đang đăng nhập
        if (userName.equals(account) || SecurityUtil.hasCurrentUserAnyOfAuthorities("admin")) {
            User user = userRepository.findByUsername(userName);
            if (user == null) {
                throw new BusinessException(Translator.toLocale("Không tồn tại"));
            } else {
                return new UserDTO(user);
            }
        }
        throw new BusinessException(Translator.toLocale("Không có quyền truy cập"));
    }

    /**
     * Cập nhật thông tin tài khoản có tên userName bằng đối tượng UserDTO cung cấp.
     *
     * @param userName Tên người dùng cần được cập nhật thông tin.
     * @param userDTO  : id name email avatar status.
     * @throws BusinessException Ném ra khi có lỗi xảy ra trong quá trình cập nhật hoặc người dùng không tồn tại
     *                           hoặc không có quyền truy cập.
     */
    @Override
    public void updateProfile(String userName, UserDTO userDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String account = authentication.getName();
        // Kiểm tra tài khoản đăng nhập có quyền admin hoặc tài khoản bị sửa có trùng tên tài khoản đang đăng nhập
        if (SecurityUtil.hasCurrentUserThisAuthority("admin") || account.equals(userName)) {
            User existingUser = userRepository.findByUsername(userName);

            if (existingUser == null) {
                throw new BusinessException(Translator.toLocale("Người dùng không tồn tại"));
            }

            if (userDTO.getId() != null) {
                throw new BusinessException(Translator.toLocale("Không sửa id"));
            }
            // Kiểm tra trùng email
            User userByEmail = userRepository.findByEmailIgnoreCase(userDTO.getEmail());
            if (userByEmail != null && !userByEmail.getId().equals(existingUser.getId())) {
                throw new BusinessException(Translator.toLocale("Email đã tồn tại"));
            }
            // Kiểm tra trùng userName
            User userByUserName = userRepository.findByUsername(userDTO.getUsername());
            if (userByUserName != null && !userByUserName.getUsername().equals(existingUser.getUsername())) {
                throw new BusinessException(Translator.toLocale("UserName đã tồn tại"));
            }

            existingUser.setUsername(userDTO.getUsername());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setAvatar(userDTO.getAvatar());
            existingUser.setStatus(userDTO.getStatus());

            userRepository.save(existingUser);
        } else {
            throw new BusinessException(Translator.toLocale("Không có quyền truy cập"));
        }
    }

}
