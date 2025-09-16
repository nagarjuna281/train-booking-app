package com.example.trainbooking.repository;

import com.example.trainbooking.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TrainRepository extends JpaRepository<Train, Long> {
    List<Train> findByRouteAndDepartureTimeAfter(String route, LocalDateTime date);
}
