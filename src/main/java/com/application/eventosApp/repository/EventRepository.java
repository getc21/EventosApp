package com.application.eventosApp.repository;

import com.application.eventosApp.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findEventsByUserId(Long userId);
}
