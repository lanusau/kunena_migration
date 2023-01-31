package com.freedomfromfeargroup.migrate.jpa.afi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PunForumRepository extends JpaRepository<PunForumEntity, Integer> {
    Optional<PunForumEntity> getByForumName(String forumName);
}
