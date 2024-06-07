package com.application.eventosApp.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Invitation {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Event event;

    @ManyToOne
    private User user;

    private boolean assistance;
    private LocalDate date_assistance;
    private LocalTime time_assistance;
    private String picture;
    private String coordinates;

}
