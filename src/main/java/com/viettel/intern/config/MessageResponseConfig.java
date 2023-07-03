package com.viettel.intern.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(
        prefix = "message-response"
)
@Getter
@Setter
public class MessageResponseConfig {
    private Map<String, String> params;
}
