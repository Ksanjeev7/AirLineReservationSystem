package com.Airline.DTO;

import com.Airline.Model.Role;

import lombok.Data;

@Data
public class RegisterRequest {

	private Integer id;
	private String userName;
	private String password;
	private Role role;
}
