package com.Airline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Airline.Model.Booking;
import com.Airline.Model.Users;

@Repository
public interface BookingRepository  extends JpaRepository<Booking, Long>{

	List<Booking> findByUser(Users user);
    List<Booking> findByUserId(Long userId);
}
