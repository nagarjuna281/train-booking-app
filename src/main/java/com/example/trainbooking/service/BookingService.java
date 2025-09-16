package com.example.trainbooking.service;

import com.example.trainbooking.model.Booking;
import com.example.trainbooking.model.Train;
import com.example.trainbooking.repository.BookingRepository;
import com.example.trainbooking.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired private BookingRepository bookingRepository;
    @Autowired private TrainRepository trainRepository;

    public Booking bookTicket(Long trainId, String userName, int seats) {
        Train train = trainRepository.findById(trainId).orElseThrow();
        if (train.getAvailableSeats() < seats) {
            throw new RuntimeException("Not enough seats");
        }
        train.setAvailableSeats(train.getAvailableSeats() - seats);
        trainRepository.save(train);

        Booking booking = new Booking();
        booking.setTrain(train);
        booking.setUserName(userName);
        booking.setSeatsBooked(seats);
        return bookingRepository.save(booking);
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        Train train = booking.getTrain();
        train.setAvailableSeats(train.getAvailableSeats() + booking.getSeatsBooked());
        trainRepository.save(train);
        bookingRepository.delete(booking);
    }

    public List<Booking> getUserBookings(String userName) {
        return bookingRepository.findByUserName(userName);
    }
}
