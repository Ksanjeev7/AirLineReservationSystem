package com.Airline.DTO;

import com.Airline.Model.Users;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FlightRequest {
    private String flightNumber;
    private String source;
    private String destination;
    private String departureTime;
    private Integer  availableSeats;
    private Long id;
    private Users userId;
}
