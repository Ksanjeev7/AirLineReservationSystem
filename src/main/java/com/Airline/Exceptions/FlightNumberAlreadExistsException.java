package com.Airline.Exceptions;

public class FlightNumberAlreadExistsException extends MyCustomException {

	private static final long serialVersionUID = 1L;

	public FlightNumberAlreadExistsException(String message) {
		super(message);
	}
}
