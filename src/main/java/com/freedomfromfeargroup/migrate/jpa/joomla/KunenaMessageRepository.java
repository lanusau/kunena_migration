package com.freedomfromfeargroup.migrate.jpa.joomla;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KunenaMessageRepository extends JpaRepository<KunenaMessageEntity, Integer> {
}
