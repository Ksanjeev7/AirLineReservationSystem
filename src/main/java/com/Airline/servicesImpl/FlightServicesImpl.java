package com.Airline.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Airline.DTO.FlightRequest;
import com.Airline.Exceptions.FlightNumberAlreadExistsException;
import com.Airline.Exceptions.MyCustomException;
import com.Airline.Model.Flight;
import com.Airline.repository.FlightRepository;
import com.Airline.repository.UserRepository;
import com.Airline.services.FlightServices;

@Service
public class FlightServicesImpl implements FlightServices {

	private final FlightRepository flightRepo;

	private final UserRepository userRepo;

	public FlightServicesImpl(FlightRepository flightRepo, UserRepository userRepo) {
		this.flightRepo = flightRepo;
		this.userRepo = userRepo;
	}

	@Override
	public Flight createFlight(FlightRequest request)   {
		try {
		if(flightRepo.existsByFlightNumber(request.getFlightNumber())) {
			throw new FlightNumberAlreadExistsException("Flight with number " + request.getFlightNumber() + " already exists");
		}
		Flight flight = Flight.builder()
				.flightNumber(request.getFlightNumber())
				.source(request.getSource())
				.destination(request.getDestination())
				.departureTime(request.getDepartureTime())
				.availableSeats(request.getAvailableSeats())
				.Id(request.getId())
				
				.build();
		return flightRepo.save(flight);
	}catch (Exception e) {
		throw new MyCustomException("Error While Creating Flight: "+ e.getMessage());
	}
	}

	@Override
	public Flight updateFlight(Long flightId, FlightRequest request) {

		Flight flight = flightRepo.findById(flightId).orElseThrow();
		if(flight.getId() == null)  {
			throw new MyCustomException("Flight with Id: " + flightId + " doesn't exists");
		}
		flight.setFlightNumber(request.getFlightNumber());
		flight.setSource(request.getSource());
		flight.setDestination(request.getDestination());
		flight.setDepartureTime(request.getDepartureTime());
		flight.setAvailableSeats(request.getAvailableSeats());
		return flightRepo.save(flight);
	}
	
	@Override
	public void deleteFlight(Long flightId) {
		if(!flightRepo.existsById(flightId)) {
			throw new MyCustomException("Flight Id not Found: "+ flightId);
		}
		flightRepo.deleteById(flightId);
	}

	@Override
	public List<Flight> searchFlights(String source, String destination) {
		try {
			if((source == null || source.trim().isEmpty()) && (destination == null || destination.trim().isEmpty())) {
				throw new MyCustomException("Both Source and Destination can't be Empty");
			}
			List<Flight> flights = flightRepo.findAll();

			List<Flight> filteredFlights  = flights.stream()
					.filter(flight -> source == null || flight.getSource().toLowerCase().contains(source.toLowerCase()))
					.filter(flight -> destination == null || flight.getDestination().toLowerCase().contains(destination.toLowerCase()))
					.collect(Collectors.toList());

			if(filteredFlights .isEmpty()) {
				throw new MyCustomException("No Flights available for the route:  " +source+ "  to " + destination);
			}
			return filteredFlights;
		}
		catch (Exception e) {
			throw new MyCustomException("Error While Searching Flights: "+ e.getMessage());
		}
	}

	@Override
	public List<Flight> getAllFlights() {
		List<Flight> allFlights = flightRepo.findAll();
		if(allFlights.isEmpty()) {
			throw new MyCustomException("No flights found");
		}
		return allFlights;
	}

	@Override
	public List<Flight> getUserFlightsByUserId(Integer user_id) {
		if(!userRepo.existsById(user_id)) {
			throw new IllegalArgumentException("User with ID " + user_id + " does not exist.");
		}
		List<Flight> userFlights = flightRepo.findByBookingsUserId(user_id);
		if(userFlights.isEmpty()) {
			throw new MyCustomException("No flights found for user with ID: " + user_id);
		}
		return userFlights;
	}
}
