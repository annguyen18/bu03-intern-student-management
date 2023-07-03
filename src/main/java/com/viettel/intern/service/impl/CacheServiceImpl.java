package com.viettel.intern.service.impl;

import com.viettel.intern.constant.CacheKey;
import com.viettel.intern.entity.base.ParamConfig;
import com.viettel.intern.repository.base.ParamConfigRepository;
import com.viettel.intern.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author KhaiBQ
 * @since 6/8/2023 - 9:44 PM
 */
@Slf4j
@Service
public class CacheServiceImpl implements CacheService {

    @Value("${spring.profiles.active}")
    private String env;

    private final ParamConfigRepository paramConfigRepository;

    private final MessageSource messageSource;

    private Map<String, String> paramMapCache = new HashMap<>();

    @Autowired
    public CacheServiceImpl(ParamConfigRepository paramConfigRepository, MessageSource messageSource) {
        this.paramConfigRepository = paramConfigRepository;
        this.messageSource = messageSource;
    }

    @PostConstruct
    @Override
    public void initConfigParam() {
        log.info("Cached param environment : {}", this.env);
        this.paramMapCache = this.paramConfigRepository.findAllByEnv(this.env)
                .stream()
                .collect(
                        Collectors.toMap(ParamConfig::getKey, ParamConfig::getValue)
                );
        log.info("Cached param size : {}", this.paramMapCache.size());
    }

    private String getParamFallback(String key) {
        String value = this.paramMapCache.get(key);
        if (value == null) {
            return this.paramConfigRepository.findFirstByKeyAndEnv(key, this.env)
                    .map(ParamConfig::getValue)
                    .orElse("");
        }
        return value;
    }

    @Override
    public String getParam(String key) {
        return this.getParam(key, "");
    }

    @Override
    public String getParam(String key, String defaultValue) {
        String value = this.getParamFallback(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public String getMessage(CacheKey key, Object... params) {
        String defaultValue = this.messageSource.getMessage(key.getProp(), params, LocaleContextHolder.getLocale());
        String message = this.getParam(key.name(), defaultValue);
        return String.format(message, params);
    }

    @Override
    public String[] getParamArray(String key) {
        return this.getParamArray(key, new String[0]);
    }

    @Override
    public String[] getParamArray(String key, String[] defaultValue) {
        String value = this.getParamFallback(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return value.split(",");
    }
}
