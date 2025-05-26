package com.Airline.services;

import java.util.List;

import com.Airline.DTO.FlightRequest;
import com.Airline.Model.Flight;

public interface FlightServices {

		Flight createFlight(FlightRequest request);
		Flight updateFlight(Long flightId, FlightRequest request);
		void deleteFlight(Long flightId);
		List<Flight> getAllFlights();
	    List<Flight> searchFlights(String source, String destination);
	    List<Flight> getUserFlightsByUserId(Integer userId);
}
