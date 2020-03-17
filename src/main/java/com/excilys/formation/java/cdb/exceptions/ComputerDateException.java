package com.excilys.formation.java.cdb.exceptions;

@SuppressWarnings("serial")
public class ComputerDateException extends Exception {
	
	public ComputerDateException() {
	}

	public ComputerDateException(String message) {
		super(message);
	}

	public ComputerDateException(Throwable cause) {
		super(cause);
	}

	public ComputerDateException(String message, Throwable cause) {
		super(message, cause);
	}

	public ComputerDateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
