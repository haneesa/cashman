package com.suncorp.api.cashman.exception;

public class CurrencyNotAvailableException extends RuntimeException {

	private static final long serialVersionUID = 2563610979276233563L;

	public CurrencyNotAvailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public CurrencyNotAvailableException(String message) {
		super(message);
	}

	public CurrencyNotAvailableException(Throwable cause) {
		super(cause);
	}

}
