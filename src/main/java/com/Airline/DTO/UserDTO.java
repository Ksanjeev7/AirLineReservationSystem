package com.Airline.DTO;

import java.util.List;

import com.Airline.Model.Role;

import lombok.Data;

@Data
public class UserDTO {

	private Integer id;
	private String userName;
	private Role role;
	private List<FlightResponse> bookedFlights;
}
