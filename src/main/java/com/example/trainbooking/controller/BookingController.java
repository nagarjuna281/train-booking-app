package com.example.trainbooking.controller;

import com.example.trainbooking.model.Train;
import com.example.trainbooking.repository.TrainRepository;
import com.example.trainbooking.service.BookingService;
import com.example.trainbooking.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class BookingController {
    @Autowired private TrainRepository trainRepository;
    @Autowired private BookingService bookingService;
    @Autowired private UserService userService;

    @GetMapping("/")
    public String home(HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "index";
        }
        return "redirect:/search";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        userService.register(username, password);
        model.addAttribute("message", "Registered successfully!");
        return "login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        if (userService.login(username, password)) {
            session.setAttribute("username", username);
            return "redirect:/search";
        }
        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchForm(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", username);
        return "search";
    }

    @PostMapping("/search")
    public String searchTrains(@RequestParam String route,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                               Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        List<Train> trains = trainRepository.findByRouteAndDepartureTimeAfter(route, date);
        model.addAttribute("trains", trains);
        model.addAttribute("username", username);
        return "search-results";
    }

    @PostMapping("/book")
    public String bookTicket(@RequestParam Long trainId, @RequestParam String username, @RequestParam int seats, Model model, HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        try {
            bookingService.bookTicket(trainId, username, seats);
            model.addAttribute("message", "Booking successful!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("username", username);
        return "booking-confirmation";
    }

    @GetMapping("/bookings")
    public String viewBookings(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("bookings", bookingService.getUserBookings(username));
        model.addAttribute("username", username);
        return "bookings";
    }

    @PostMapping("/cancel")
    public String cancelBooking(@RequestParam Long bookingId, @RequestParam String username, Model model, HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        bookingService.cancelBooking(bookingId);
        model.addAttribute("message", "Booking cancelled!");
        return "redirect:/bookings";
    }
}
