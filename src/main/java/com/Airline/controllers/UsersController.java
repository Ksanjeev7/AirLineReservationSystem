package com.Airline.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Airline.DTO.UserDTO;
import com.Airline.services.UserRegisterService;

@RestController
@RequestMapping("api/user")
public class UsersController {

	private final UserRegisterService userService;
	
	public UsersController(UserRegisterService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/{Id}")
	public ResponseEntity<?> getById(@PathVariable Integer Id, Authentication auth) {
		 String loggedInUser = auth.getName();        
		 System.out.println("Logged In User: " + loggedInUser);
		UserDetails userDetails =(UserDetails) auth.getPrincipal();
		                 boolean isAdmin= userDetails.getAuthorities().stream()
		                 .anyMatch(autho -> autho.getAuthority().equals("ROLE_ADMIN"));
		UserDTO  requestedIdUser = userService.findById(Id);
		if(!requestedIdUser.getUserName().equals(loggedInUser) && !isAdmin) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can't access other users' data.");
	}
	return ResponseEntity.ok(requestedIdUser);
	}
}
