package com.freedomfromfeargroup.migrate.jpa.afi;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PunPostRepository extends JpaRepository<PunPostEntity, Integer> {
    List<PunPostEntity> findAllByTopicId(Integer topicId, Sort sort);
}
