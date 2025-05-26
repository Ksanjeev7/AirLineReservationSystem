package com.Airline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Airline.DTO.FlightRequest;
import com.Airline.Model.Flight;
public interface FlightRepository extends JpaRepository<Flight, Long> {

	Flight save(FlightRequest req);
    boolean existsByFlightNumber(String flightId);
    @Query("SELECT f FROM Flight f WHERE LOWER(TRIM(f.source)) = LOWER(TRIM(:source)) AND LOWER(TRIM(f.destination)) = LOWER(TRIM(:destination))")
	List<Flight> findBySourceAndDestination(@Param("source") String source, @Param("destination") String destination);
    @Query("SELECT b.flight FROM Booking b WHERE b.user.id = :userId")
    List<Flight> findByBookingsUserId(@Param("userId") Integer userId);
    @Query("SELECT f FROM Flight f WHERE f.availableSeats > 0")
    List<Flight> findAvailableFlights();
}
