package com.viettel.intern.service;

import com.viettel.intern.dto.request.ForgotPasswordRequest;

public interface AuthService {
    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(String key);
}
