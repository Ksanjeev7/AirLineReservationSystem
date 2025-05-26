package com.Airline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirlineReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirlineReservationApplication.class, args);
	}
//    @Bean
//    CommandLineRunner init(JwTUtil jwtUtil) {
//        return args -> {
//            jwtUtil.generateSecureSecret();
//        };
//    }

}
