package org.kylin.authcenter.repository;

import org.kylin.authcenter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);
}
