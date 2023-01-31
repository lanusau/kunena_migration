package com.freedomfromfeargroup.migrate.jpa.afi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PunTopicRepository extends JpaRepository<PunTopicEntity, Integer> {
    List<PunTopicEntity> findAllByForumId(Integer forumId);
}
