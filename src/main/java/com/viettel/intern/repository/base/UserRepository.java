package com.viettel.intern.repository.base;

import com.viettel.intern.entity.base.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities")
    User findByUsernameIgnoreCase(String username);

    @EntityGraph(attributePaths = "authorities")
    User findByEmailIgnoreCase(String email);

    User findByUsername(String userName);

}
