package com.Airline.DTO;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class LoginRequest {
	
	private String userName;
	private String password;
	
}
