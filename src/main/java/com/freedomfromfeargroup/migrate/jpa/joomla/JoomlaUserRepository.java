package com.freedomfromfeargroup.migrate.jpa.joomla;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JoomlaUserRepository extends JpaRepository<JoomlaUserEntity, Integer> {
    boolean existsByUsername(String userName);

    Optional<JoomlaUserEntity> findByUsername(String userName);
}
