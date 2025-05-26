package com.Airline.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Airline.DTO.FlightRequest;
import com.Airline.Exceptions.MyCustomException;
import com.Airline.Model.Flight;
import com.Airline.services.FlightServices;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
	private final FlightServices flightService;

	public FlightController(FlightServices flightService) {
		this.flightService = flightService;
	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Flight>createFlight(@RequestBody FlightRequest request){
		try {
			Flight createdFlight = flightService.createFlight(request);
			return new ResponseEntity<>(createdFlight,HttpStatus.CREATED);
		} catch ( MyCustomException  e) {
			throw e;
		}
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody FlightRequest request){

		try {
			Flight updatedFlight = flightService.updateFlight(id, request);
			return ResponseEntity.ok(updatedFlight);
		} catch (MyCustomException e) {
			throw e;
		}
	}

	@GetMapping("/bookings/{userId}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN') and @userSecurity.hasUserId(authentication, #userId)")
	public ResponseEntity<List<Flight>> getUserBookings(@PathVariable Integer userId) {
		try {
			return ResponseEntity.ok(flightService.getUserFlightsByUserId(userId));
		} catch (MyCustomException e) {
			throw e;
		}
	}

	@GetMapping("/all")
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	public ResponseEntity<List<Flight>>getAllFlights(){
		try {
			return ResponseEntity.ok(flightService.getAllFlights());
		} catch (MyCustomException e) {
			throw e;
		}
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteFlight(@PathVariable Long id){
		try {
			flightService.deleteFlight(id);
			return ResponseEntity.status(HttpStatus.OK).body("Fight Deleted Successfully");
		} catch (MyCustomException e) {
			throw e;
		}
	}

	@GetMapping("/search")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<List<Flight>> searchFlights(
			@RequestParam(required = false) String source, 
			@RequestParam(required = false) String destination) {
		try {
			List<Flight> flights = flightService.searchFlights(source, destination);
			return ResponseEntity.ok(flights);
		} catch (MyCustomException  e) {
			throw e;
		}
	}
}
