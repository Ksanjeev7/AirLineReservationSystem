package com.Airline.DTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FlightResponse {
	  	private Long id;
	    private String flightNumber;
	    private String source;
	    private String destination;
	    private String departureTime;
	    private int availableSeats;
//	    private String bookedBy;
	}