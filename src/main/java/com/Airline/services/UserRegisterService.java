package com.Airline.services;

import java.util.List;
import java.util.Optional;

import com.Airline.DTO.UserDTO;
import com.Airline.Model.Users;

public interface UserRegisterService {
	Optional<Users>registerUser(Users user);
	UserDTO findById(Integer Id);
	List<Users> findAll();
}
