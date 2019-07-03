package com.algaworks.brewer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CepException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CepException(String message) {
		super(message);
	}

}
