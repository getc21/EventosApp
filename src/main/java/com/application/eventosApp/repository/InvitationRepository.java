package com.application.eventosApp.repository;

import com.application.eventosApp.entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Optional<Invitation> findByEventIdAndUserId(Long eventId, Long userId);
}

