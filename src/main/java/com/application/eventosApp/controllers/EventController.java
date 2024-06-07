package com.application.eventosApp.controllers;

import com.application.eventosApp.entities.Event;
import com.application.eventosApp.entities.Invitation;
import com.application.eventosApp.entities.User;
import com.application.eventosApp.repository.EventRepository;
import com.application.eventosApp.repository.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event newEvent = eventRepository.save(event);
        return ResponseEntity.ok(newEvent);
    }

    @PostMapping("/{eventId}/invite")
    public ResponseEntity<Invitation> inviteUser(@PathVariable Long eventId, @RequestBody User user) {
        Invitation invitation = new Invitation();
        invitation.setEvent(eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Evento no encontrado")));
        invitation.setUser(user);
        Invitation newInvitation = invitationRepository.save(invitation);
        return ResponseEntity.ok(newInvitation);
    }

    @PostMapping("/{eventId}/attend")
    public ResponseEntity<Invitation> recordAttendance(@PathVariable Long eventId, @RequestBody AttendRequest request) {
        Invitation invitation = invitationRepository.findByEventIdAndUserId(eventId, request.getUserId())
                .orElseThrow(() -> new RuntimeException("Invitación no encontrada"));
        Event event = invitation.getEvent();
        LocalTime currentTime = LocalTime.now();

        if (currentTime.isAfter(event.getStartTime()) && currentTime.isBefore(event.getEndTime())) {
            invitation.setAttendance(true);
            invitation.setDateAttendance(request.getDateAttendance());
            invitation.setTimeAttendance(request.getTimeAttendance());
            invitation.setPicture(request.getPicture());
            invitation.setcoordinates(request.getcoordinates());
            Invitation actualizada = invitationRepository.save(invitation);
            return ResponseEntity.ok(actualizada);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Event>> getEventsByUser(@PathVariable Long userId) {
        List<Event> events = eventRepository.findEventsByUserId(userId);
        return ResponseEntity.ok(events);
    }
}
