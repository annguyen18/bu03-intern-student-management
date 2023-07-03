package com.viettel.intern.service.impl;

import com.viettel.intern.config.locale.Translator;
import com.viettel.intern.dto.UserDTO;
import com.viettel.intern.dto.request.UserRegister;
import com.viettel.intern.dto.request.UserUpdate;
import com.viettel.intern.entity.base.User;
import com.viettel.intern.exception.BusinessException;
import com.viettel.intern.repository.base.UserRepository;
import com.viettel.intern.service.UserService;
import com.viettel.intern.ultis.CommonUtil;
import com.viettel.intern.ultis.EmailUtil;
import com.viettel.intern.ultis.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
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

    /**
     * Get all user in database
     *
     * @return List<User>
     */
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    /**
     * Đăng ký người dùng dựa trên đối tượng UserRegister.
     *
     * @param userRegister Đối tượng UserRegister chứa thông tin đăng ký người dùng.
     * @throws BusinessException Nếu email không hợp lệ, email đã tồn tại, hoặc tên người dùng đã tồn tại.
     */
    @Override
    public void register(UserRegister userRegister) {
        if (SecurityUtil.hasCurrentUserAnyOfAuthorities("admin")) {
            if (!CommonUtil.isValidEmail(userRegister.getEmail())) {
                throw new BusinessException(Translator.toLocale("email.invalid"));
            }
            if (userRepository.findByEmailIgnoreCase(userRegister.getEmail()) != null) {
                throw new BusinessException(Translator.toLocale("email.exists"));
            }
            if (userRepository.findByUsernameIgnoreCase(userRegister.getUsername()) != null) {
                throw new BusinessException(Translator.toLocale("username.exists"));
            }
            userRegister.setPassword(passwordEncoder.encode(userRegister.getPassword()));
            User user = new User();
            user.setUsername(userRegister.getUsername());
            user.setPassword(userRegister.getPassword());
            user.setEmail(userRegister.getEmail());
            user.setAuthorities(userRegister.getAuthorities());
            user.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            userRepository.save(user);
        } else {
            throw new BusinessException(Translator.toLocale("user.not-authorized"));
        }
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


    /**
     * Cập nhật thông tin người dùng dựa trên đối tượng UserUpdate.
     *
     * @param userUpdate Đối tượng UserUpdate chứa thông tin cập nhật người dùng.
     * @throws BusinessException Nếu người dùng không tồn tại hoặc không có quyền truy cập.
     */
    @Override
    public void update(UserUpdate userUpdate) {
        if(SecurityUtil.hasCurrentUserAnyOfAuthorities("admin")) {
            User userInDB = userRepository.findByEmailIgnoreCase(userUpdate.getEmail());
            if (userInDB == null) {
                throw new BusinessException(Translator.toLocale("user.not-exists"));
            }
            userInDB.setEmail(userUpdate.getEmail());
            userInDB.setStatus(userUpdate.getStatus());
            userInDB.setAvatar(userUpdate.getAvatar());
            userInDB.setAuthorities(userUpdate.getAuthorities());
            userRepository.save(userInDB);
        } else {
            throw new BusinessException(Translator.toLocale("user.not-authorized"));
        }
    }

    /**
     * Xóa người dùng theo tên người dùng.
     *
     * @param username Tên người dùng cần xóa.
     * @throws BusinessException Nếu người dùng không tồn tại hoặc không có quyền truy cập.
     */
    @Override
    public void delete(String username) {
        if (SecurityUtil.hasCurrentUserAnyOfAuthorities("admin")) {
            User userInDB = userRepository.findByUsernameIgnoreCase(username);
            if (userInDB == null) {
                throw new BusinessException(Translator.toLocale("user.not-exists"));
            }
            userRepository.delete(userInDB);
        } else {
            throw new BusinessException(Translator.toLocale("user.not-authorized"));
        }
    }
}
