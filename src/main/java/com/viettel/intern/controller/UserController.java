package com.viettel.intern.controller;


import com.viettel.intern.config.factory.response.GeneralResponse;
import com.viettel.intern.config.factory.response.ResponseFactory;
import com.viettel.intern.entity.base.User;
import com.viettel.intern.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<GeneralResponse<Object>> register(@RequestBody User user) {
        userService.register(user);
        return responseFactory.successNoData();
    }

}
