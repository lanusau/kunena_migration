package com.freedomfromfeargroup.migrate.jpa.afi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PunUserRepository extends JpaRepository<PunUserEntity, Integer> {
}
