package com.viettel.intern.controller;

import com.viettel.intern.config.factory.response.GeneralResponse;
import com.viettel.intern.config.factory.response.ResponseFactory;
import com.viettel.intern.config.security.jwt.JwtFilter;
import com.viettel.intern.config.security.jwt.JwtUtil;
import com.viettel.intern.dto.request.ForgotPasswordRequest;
import com.viettel.intern.dto.request.LoginRequest;
import com.viettel.intern.dto.response.LoginResponse;
import com.viettel.intern.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final ResponseFactory responseFactory;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse<Object>> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        if (authentication.isAuthenticated()) {
            String token = jwtUtil.createToken(authentication, request.getRemember());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer "
                    + token);
            return responseFactory.successWithHeader(httpHeaders, new LoginResponse(token));
        }
        return null;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<GeneralResponse<Object>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request);
        return responseFactory.successNoData();
    }

    @GetMapping("/reset-password")
    public ResponseEntity<GeneralResponse<Object>> resetPassword(@RequestParam String key) {
        authService.resetPassword(key);
        return responseFactory.successNoData();
    }
}
