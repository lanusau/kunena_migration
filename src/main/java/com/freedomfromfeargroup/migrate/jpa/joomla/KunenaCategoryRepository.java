package com.freedomfromfeargroup.migrate.jpa.joomla;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KunenaCategoryRepository extends JpaRepository<KunenaCategoryEntity, Integer> {
    Optional<KunenaCategoryEntity> getByName(String name);
}
