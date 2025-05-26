package com.Airline.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Airline.DTO.BookingRequest;
import com.Airline.DTO.BookingResponse;
import com.Airline.DTO.FlightResponse;
import com.Airline.Model.Users;
import com.Airline.repository.UserRepository;
import com.Airline.services.BookingService;

@RestController
@RequestMapping("api/bookings")
@PreAuthorize("hasRole('USER')")  // Enforce user-only access
public class BookingConroller {
	private final BookingService bookingService;
	private final UserRepository userRepo;

	public BookingConroller(BookingService bookingService, UserRepository userRepo) {
		this.bookingService = bookingService;
		this.userRepo = userRepo;
	}

	@PostMapping("/book")
	public ResponseEntity<BookingResponse> bookFlight(@RequestBody BookingRequest request, Authentication auth) {
		Users user = userRepo.findByUserName(auth.getName()).orElseThrow();
		return ResponseEntity.ok(bookingService.bookFlight(request, user));
	}

	@GetMapping("/my-bookings")
	public ResponseEntity<List<BookingResponse>> myBookings(Authentication auth) {
		Users user = userRepo.findByUserName(auth.getName()).orElseThrow();
		return ResponseEntity.ok(bookingService.getBookingsByUser(user));
	}

	@DeleteMapping("/cancel/{id}")
	public ResponseEntity<?>cancel(@PathVariable Long id, Authentication auth){
		Users user = userRepo.findByUserName(auth.getName()).orElseThrow();
		bookingService.cancelBooking(id, user);
		return ResponseEntity.ok("Booking Cancelled");
	}

	@GetMapping("/available-flights")
	public ResponseEntity<List<FlightResponse>>getAvailableFlights(@RequestParam String source, @RequestParam String destination, @RequestParam String Date ){
		try {
			List<FlightResponse> availableFlightsFiltered = bookingService.getAvailableFlightsFiltered(source, destination, Date);

			return ResponseEntity.ok(availableFlightsFiltered);
		} catch (Exception e) {
			throw e;
		}	
	}
}