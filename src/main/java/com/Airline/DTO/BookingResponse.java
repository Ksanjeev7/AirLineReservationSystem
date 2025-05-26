package com.Airline.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookingResponse {

	private Long bookingId;
    private String username;
    private String flightNumber;
    private String source;
    private String destination;
    private String departureTime;
    private String bookingTime;
}
