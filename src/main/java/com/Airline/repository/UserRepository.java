package com.Airline.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Airline.Model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

	Optional<Users>findByUserName(String name);
}
