package com.viettel.intern.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseStatusCodeEnum {
    public static final ResponseStatusCode SUCCESS = ResponseStatusCode.builder().code("00").httpCode(200).build();
    public static final ResponseStatusCode BUSINESS_ERROR = ResponseStatusCode.builder().code("BSA0001").httpCode(400).build();
    public static final ResponseStatusCode VALIDATION_ERROR = ResponseStatusCode.builder().code("BSA0002").httpCode(400).build();
    public static final ResponseStatusCode INTERNAL_GENERAL_SERVER_ERROR = ResponseStatusCode.builder().code("BSA0003").httpCode(500).build();
    public static final ResponseStatusCode ERROR_BODY_CLIENT = ResponseStatusCode.builder().code("BSA0004").httpCode(400).build();
    public static final ResponseStatusCode ERROR_BODY_REQUIRED = ResponseStatusCode.builder().code("BSA0005").httpCode(400).build();
    public static final ResponseStatusCode DATA_NOT_FOUND = ResponseStatusCode.builder().code("01").httpCode(400).build();
    public static final ResponseStatusCode TOKEN_ERROR = ResponseStatusCode.builder().code("02").httpCode(401).build();
    public static final ResponseStatusCode USERNAME_PASSWORD_ERROR = ResponseStatusCode.builder().code("03").httpCode(401).build();
    public static final ResponseStatusCode INACTIVE_ACCOUNT = ResponseStatusCode.builder().code("04").httpCode(401).build();
    public static final ResponseStatusCode ACCESS_DENIED = ResponseStatusCode.builder().code("05").httpCode(403).build();
}
