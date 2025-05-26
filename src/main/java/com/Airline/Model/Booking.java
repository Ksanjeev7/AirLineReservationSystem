package com.Airline.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Bookings")
@Builder
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	 	@ManyToOne(fetch = FetchType.LAZY)
	 	@JoinColumn(name = "user_id")
	    private Users user;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "flight_id")
	    private Flight flight;

	    @Column(nullable = false)
	    private String bookingTime;
	}