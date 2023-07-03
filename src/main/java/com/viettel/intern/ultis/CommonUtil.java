package com.viettel.intern.ultis;

import com.viettel.intern.constant.AppConstant;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class CommonUtil {

    public boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        return phoneNumber.matches(AppConstant.REGEX_PHONE_NUMBER);
    }

    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return email.matches(AppConstant.REGEX_EMAIL);
    }
}
