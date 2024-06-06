package com.foodsafety.repository;

import com.foodsafety.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event> findAllByGroupId(@Param("groupId") String groupId);
}
