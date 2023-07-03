package com.viettel.intern.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author KhaiBQ
 * @since 6/8/2023 - 9:43 PM
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CacheKey {
    APP_NAME("APP.NAME"),
    ;

    private final String prop;
}
