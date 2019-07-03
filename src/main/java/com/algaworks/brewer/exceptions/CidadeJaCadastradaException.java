package com.algaworks.brewer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CidadeJaCadastradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CidadeJaCadastradaException() {
		super();
	}

	public CidadeJaCadastradaException(String message, Throwable cause) {
		super(message, cause);
	}

	public CidadeJaCadastradaException(String message) {
		super(message);
	}

	public CidadeJaCadastradaException(Throwable cause) {
		super(cause);
	}

}
