package com.viettel.intern.repository.base;

import com.viettel.intern.entity.base.ParamConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author KhaiBQ
 * @since 6/8/2023 - 9:39 PM
 */
public interface ParamConfigRepository extends JpaRepository<ParamConfig, Long> {
    List<ParamConfig> findAllByEnv(String env);

    Optional<ParamConfig> findFirstByKeyAndEnv(String key, String env);

    Optional<ParamConfig> findByKey(String key);
}
