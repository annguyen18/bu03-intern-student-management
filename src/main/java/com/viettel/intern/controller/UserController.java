package com.viettel.intern.controller;


import com.viettel.intern.config.factory.response.GeneralResponse;
import com.viettel.intern.config.factory.response.ResponseFactory;
import com.viettel.intern.dto.request.UserRegister;
import com.viettel.intern.dto.request.UserUpdate;
import com.viettel.intern.dto.UserDTO;
import com.viettel.intern.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final ResponseFactory responseFactory;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<GeneralResponse<Object>> register(@RequestBody UserRegister user) {
        userService.register(user);
        return responseFactory.successNoData();
    }

    @PostMapping("/update")
    public ResponseEntity<GeneralResponse<Object>> update(@RequestBody UserUpdate user) {
        userService.update(user);
        return responseFactory.successNoData();
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<GeneralResponse<Object>> delete(@PathVariable String username) {
        userService.delete(username);
        return responseFactory.successNoData();
    }

    @GetMapping("/profile")
    public ResponseEntity<GeneralResponse<UserDTO>> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String account = authentication.getName();
        UserDTO userDTO = userService.getProfile(account);
        return responseFactory.success(userDTO);
    }

    @GetMapping("/profile/{userName}")
    public ResponseEntity<GeneralResponse<Object>> getProfiles(@PathVariable String userName) {
        UserDTO userDTO = userService.getProfile(userName);
        return responseFactory.success(userDTO);
    }

    @PutMapping("/profile/{userName}")
    public ResponseEntity<GeneralResponse<Object>> updateProfileByAdmin(@PathVariable String userName,
                                                                        @RequestBody UserDTO userDTO) {
        userService.updateProfile(userName, userDTO);
        return responseFactory.successNoData();
    }

    @PutMapping("/profile")
    public ResponseEntity<GeneralResponse<Object>> updateProfile(@RequestBody UserDTO userDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String account = authentication.getName();
        userService.updateProfile(account, userDTO);
        return responseFactory.successNoData();
    }
}
