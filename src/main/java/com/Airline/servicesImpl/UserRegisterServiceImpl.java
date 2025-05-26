package com.Airline.servicesImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Airline.DTO.FlightResponse;
import com.Airline.DTO.UserDTO;
import com.Airline.Model.Booking;
import com.Airline.Model.Flight;
import com.Airline.Model.Users;
import com.Airline.repository.UserRepository;
import com.Airline.services.UserRegisterService;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder encoder;
	
	
	@Override
	public Optional<Users> registerUser(Users user) {
		if(userRepo.findByUserName(user.getUsername()).isPresent()) {
			return Optional.empty();
		}
		user.setPassword(encoder.encode(user.getPassword()));
		Users newUser = userRepo.save(user);
		return Optional.of(newUser);
	}

	@Override
	public UserDTO findById(Integer Id) {
		Users user = userRepo.findById(Id)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + Id));
		return convertToUserDTO(user);
	}

	@Override
	public List<Users> findAll() {
		return userRepo.findAll();
	}
	
	private UserDTO  convertToUserDTO(Users user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setUserName(user.getUsername());
		userDTO.setRole(user.getRole());
		// Convert the booked flights to FlightResponse DTOs
		List<FlightResponse> flightsResponses = user.getBookings().stream()
				.map(this::convertToFlightResponse)
				.collect(Collectors.toList());
		
		userDTO.setBookedFlights(flightsResponses);
		return userDTO;
	}

	private FlightResponse convertToFlightResponse(Booking booking) {
		//getting the flight from the Bookings
		Flight flight = booking.getFlight();
		return FlightResponse.builder()
				.id(flight.getId())
				.flightNumber(flight.getFlightNumber())
				.source(flight.getSource())
				.destination(flight.getDestination())
				.departureTime(flight.getDepartureTime())
				.availableSeats(flight.getAvailableSeats())
				.build();
	}
}
