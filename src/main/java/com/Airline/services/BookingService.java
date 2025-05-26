package com.Airline.services;

import java.util.List;

import com.Airline.DTO.BookingRequest;
import com.Airline.DTO.BookingResponse;
import com.Airline.DTO.FlightResponse;
import com.Airline.Model.Users;

public interface BookingService {
		BookingResponse bookFlight(BookingRequest request, Users user);
	    List<BookingResponse> getBookingsByUser(Users user);
	    void cancelBooking(Long bookingId, Users user);
	    List<FlightResponse>getAvailableFlightsFiltered(String source, String destination, String Date);
}
