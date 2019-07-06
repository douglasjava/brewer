package com.algaworks.brewer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NomeEstiloJaCadastradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NomeEstiloJaCadastradoException(String message) {
		super(message);
	}

}
