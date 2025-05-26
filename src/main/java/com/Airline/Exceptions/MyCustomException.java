package com.Airline.Exceptions;

public class MyCustomException  extends RuntimeException{

	private static final long serialVersionUID = 1L;

public MyCustomException(String mssg) {
 super(mssg); 
}
}
