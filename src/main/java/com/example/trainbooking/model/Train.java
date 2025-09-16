package com.example.trainbooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Train {
    @Id @GeneratedValue
    private Long id;
    private String route;
    private LocalDateTime departureTime;
    private int availableSeats;
}
