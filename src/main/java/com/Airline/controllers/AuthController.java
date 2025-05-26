package com.Airline.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Airline.DTO.AuthResponse;
import com.Airline.DTO.LoginRequest;
import com.Airline.DTO.RegisterRequest;
import com.Airline.Model.Users;
import com.Airline.Security.JwTUtil;
import com.Airline.services.UserRegisterService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserRegisterService userService;
	private  final JwTUtil jwtUtil;
	private final  AuthenticationManager authenticationManager;
	
	public AuthController(AuthenticationManager authenticationManager, UserRegisterService userService, JwTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request){
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
					Users user = (Users)authentication.getPrincipal();
			
			String token = jwtUtil.generateToken(user);
//			System.out.println("Token Generated: "+token);
	        System.out.println("Authentication successful for user: " + user.getUsername());
			return ResponseEntity.ok(
					AuthResponse.builder()
					.token(token)
					.userName(user.getUsername())
					.role(user.getRole())
					.build());
		}catch (org.springframework.security.core.AuthenticationException e) {
	        System.out.println("Authentication failed: " + e.getMessage());
			return ResponseEntity.status(401).body("Invalid UserName & Password....");
		}
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<?>register(@RequestBody RegisterRequest request) {
		Users userBuilder = Users.builder()
											.userName(request.getUserName())
											.password(request.getPassword())
											.role(request.getRole())
											.build();
		Optional<Users> user = userService.registerUser(userBuilder);
		System.out.println("user Inserted : " + user);
		if(user.isPresent()) {
			return ResponseEntity.ok(user.get());
		}else {
			return ResponseEntity.badRequest().body("Users Already Exits");
		}
	}
}
