package com.viettel.intern.config.cache;

import com.viettel.intern.config.properties.RedisCacheConfigurationProperties;
import io.lettuce.core.ReadFrom;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Configuration
@ConditionalOnProperty(
        value = {"app.cache.redis.enable"},
        havingValue = "true"
)
public class RedisCacheConfig {
    private static final Logger log = LoggerFactory.getLogger(RedisCacheConfig.class);
    @Value("${app.application-short-name}")
    private String applicationShortName;

    @Autowired
    private RedisCacheConfigurationProperties properties;

    private RedisCacheConfiguration createCacheConfiguration(long timeoutInSeconds) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer(loader);
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jdkSerializer);
        return RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair).computePrefixWith(cacheName -> {
            String prefix = this.applicationShortName + "::";
            if (!StringUtils.isEmpty(cacheName)) {
                prefix = prefix + cacheName + "::";
            }

            return prefix;
        }).entryTtl(Duration.ofSeconds(timeoutInSeconds));
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        log.info("Redis (/Lettuce) configuration enabled. With cache timeout {} seconds.", properties.getTimeoutSeconds());
        if (!CollectionUtils.isEmpty(properties.getNodes())) {
            log.info("Redis Cluster: {}", properties.getNodes());
            LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder().readFrom(ReadFrom.MASTER).build();
            RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(properties.getNodes());
            if (!StringUtils.isEmpty(properties.getPassword())) {
                redisClusterConfiguration.setPassword(RedisPassword.of(properties.getPassword()));
            }

            return new LettuceConnectionFactory(redisClusterConfiguration, clientConfiguration);
        } else {
            log.info("Redis Standalone: {}:{}", properties.getHost(), properties.getPort());
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setHostName(properties.getHost());
            redisStandaloneConfiguration.setPort(properties.getPort());
            if (!StringUtils.isEmpty(properties.getPassword())) {
                redisStandaloneConfiguration.setPassword(RedisPassword.of(properties.getPassword()));
            }

            return new LettuceConnectionFactory(redisStandaloneConfiguration);
        }
    }

    @Primary
    @Bean
    public RedisTemplate<Byte[], Byte[]> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<Byte[], Byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    @Bean({"RedisTemplateByteByte"})
    public RedisTemplate<byte[], byte[]> redisTemplateByteByte(RedisConnectionFactory cf) {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    @Bean({"RedisTemplateStringObject"})
    public RedisTemplate<String, Object> redisTemplateStringObject(RedisConnectionFactory cf) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    @Bean({"RedisTemplateObjectObject"})
    public RedisTemplate<Object, Object> redisTemplateObjectObject(RedisConnectionFactory cf) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration(RedisCacheConfigurationProperties properties) {
        return this.createCacheConfiguration(properties.getTimeoutSeconds());
    }

    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory, RedisCacheConfigurationProperties properties) {
        redisConnectionFactory.getConnection();
        log.info("Redis check connect Success");
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        Iterator<Map.Entry<String, Long>> var4 = properties.getCacheExpirations().entrySet().iterator();

        while (var4.hasNext()) {
            Map.Entry<String, Long> cacheNameAndTimeout = var4.next();
            cacheConfigurations.put(cacheNameAndTimeout.getKey(), this.createCacheConfiguration(cacheNameAndTimeout.getValue()));
        }

        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(this.cacheConfiguration(properties)).withInitialCacheConfigurations(cacheConfigurations).build();
    }
}
