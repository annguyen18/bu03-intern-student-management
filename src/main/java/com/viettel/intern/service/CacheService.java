package com.viettel.intern.service;

import com.viettel.intern.constant.CacheKey;

/**
 * @author KhaiBQ
 * @since 6/8/2023 - 9:41 PM
 */
public interface CacheService {

    void initConfigParam();

    String getParam(String key);

    String getParam(String key, String defaultValue);

    String getMessage(CacheKey key, Object... params);

    String[] getParamArray(String key);

    String[] getParamArray(String key, String[] defaultValue);
}
