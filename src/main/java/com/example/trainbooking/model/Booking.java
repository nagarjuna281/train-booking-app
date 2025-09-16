package com.example.trainbooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Booking {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    private Train train;
    private String userName;
    private int seatsBooked;
}
