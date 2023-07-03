package com.viettel.intern.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(
        prefix = "app.cache.redis"
)
@Getter
@Setter
public class RedisCacheConfigurationProperties {
    private boolean enable;
    private long timeoutSeconds = 60L;
    private int port = 6379;
    private String host = "localhost";
    private List<String> nodes = new ArrayList<>();
    private Map<String, Long> cacheExpirations = new HashMap<>();
    private String password = "";
}
