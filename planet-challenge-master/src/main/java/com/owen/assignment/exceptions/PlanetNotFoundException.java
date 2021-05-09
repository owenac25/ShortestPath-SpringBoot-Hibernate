package com.owen.assignment.exceptions;
 
/**
  * 
  * Basic Custom Exception for cases where planets are not found 
  *
  */
public class PlanetNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public PlanetNotFoundException(String planetNode, Throwable err) {
	    super("Could not find planet " + planetNode, err);
	  }
	}