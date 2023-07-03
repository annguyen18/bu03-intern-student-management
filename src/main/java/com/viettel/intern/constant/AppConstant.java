package com.viettel.intern.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppConstant {
    public static final String VIETNAMESE_LANGUAGE_TAG = "vi-VN";

    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String REGEX_PHONE_NUMBER = "^(0|84)[0-9]{9,12}$";
    public static final String REGEX_EMAIL = "^[a-zA-Z0-9]+(?:[._-][a-zA-Z0-9]+)*@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}(?:\\.[a-zA-Z]{2,})?$";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ActiveStatus {
        public static final Integer ACTIVE = 1;
        public static final Integer INACTIVE = 0;
    }
}
