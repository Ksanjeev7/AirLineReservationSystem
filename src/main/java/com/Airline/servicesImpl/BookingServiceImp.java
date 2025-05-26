package com.Airline.servicesImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Airline.DTO.BookingRequest;
import com.Airline.DTO.BookingResponse;
import com.Airline.DTO.FlightResponse;
import com.Airline.Exceptions.MyCustomException;
import com.Airline.Model.Booking;
import com.Airline.Model.Flight;
import com.Airline.Model.Users;
import com.Airline.repository.BookingRepository;
import com.Airline.repository.FlightRepository;
import com.Airline.services.BookingService;

@Service
public class BookingServiceImp implements BookingService {

	private final FlightRepository flightRepo;
	private final BookingRepository bookingRepo;

	public BookingServiceImp(FlightRepository flightRepo, BookingRepository bookingRepo) {
		this.flightRepo = flightRepo;
		this.bookingRepo = bookingRepo;
	}

	@Override
	public BookingResponse bookFlight(BookingRequest request, Users user) {
		Flight flight  = flightRepo.findById(request.getFlightId()).orElseThrow(()-> new MyCustomException("Flight not found"));
		String time = flight.getDepartureTime();
		LocalDateTime departureTime ;
		
		try {
			departureTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		} catch (Exception e) {
			throw new MyCustomException("Invalid departure time format");
		}

		if(departureTime.isBefore(LocalDateTime.now())) throw new MyCustomException("Flight already departed");
		if(flight.getAvailableSeats() <= 0 ) throw  new MyCustomException("No Seats Available");

		flight.setAvailableSeats(flight.getAvailableSeats() - 1);
		Booking booking = Booking.builder()
				.flight(flight)
				.user(user)
				.bookingTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
				.build();
		bookingRepo.save(booking);
		flightRepo.save(flight);
		return toResponse(booking);
	}

	@Override
	public List<BookingResponse> getBookingsByUser(Users user) {
		return 	bookingRepo.findByUser(user).stream().map(this::toResponse).collect(Collectors.toList());
	}

	@Override
	public void cancelBooking(Long bookingId, Users user) {

		Booking booking = bookingRepo.findById(bookingId).orElseThrow();

		if(!booking.getUser().getUsername().equalsIgnoreCase(user.getUsername())){

			throw new MyCustomException("You are not authorized to cancel this booking");
		}
		Flight flight = booking.getFlight();
		flight.setAvailableSeats(flight.getAvailableSeats() + 1);
		flightRepo.save(flight);
		bookingRepo.delete(booking);

		System.out.println("Flight Cancelled Successfully....");
	}
	private BookingResponse toResponse(Booking booking) {
		Flight flight = booking.getFlight();
		return BookingResponse.builder()
				.bookingId(booking.getId())
				.username(booking.getUser().getUsername())
				.flightNumber(flight.getFlightNumber())
				.source(flight.getSource())
				.destination(flight.getDestination())
				.departureTime(flight.getDepartureTime())
				.bookingTime(booking.getBookingTime())
				.build();
	}

	@Override
	public List<FlightResponse> getAvailableFlightsFiltered(String source, String destination, String Date) {

		try {
			List<Flight> availableFlights = flightRepo.findAvailableFlights();

			if(availableFlights == null || availableFlights.isEmpty()) {
				throw  new MyCustomException("No Fights are currently Available");
			}
			if (source != null && !source.isBlank()) {
				availableFlights = availableFlights.stream()
						.filter(f -> f.getSource().equalsIgnoreCase(source.trim()))
						.collect(Collectors.toList());
			}

			if (destination != null && !destination.isBlank()) {
				availableFlights = availableFlights.stream()
						.filter(f -> f.getDestination().equalsIgnoreCase(destination.trim()))
						.collect(Collectors.toList());
			}
			if(Date!= null && Date.isBlank()){
				availableFlights = availableFlights.stream()
						.filter(f -> f.getDepartureTime().equalsIgnoreCase(Date.trim()))
						.collect(Collectors.toList());
			}
			if (availableFlights.isEmpty()) {
				throw  new MyCustomException("No flights found for the given criteria");
			}
			//Convert Flights entities to FlightResponse DTOs
			return availableFlights.stream()
					.map(this::convertToFlightResponse)
					.collect(Collectors.toList());

		} catch (MyCustomException e) {
			throw e;
		}catch(Exception ex) {

			throw new MyCustomException("Error while fetching available flights: " + ex.getMessage());
		}
	}
	private FlightResponse convertToFlightResponse(Flight flight) {
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
