package com.algaworks.brewer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CpfCnpjClienteJaCadastradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CpfCnpjClienteJaCadastradoException() {
		super();
	}

	public CpfCnpjClienteJaCadastradoException(String message, Throwable cause) {
		super(message, cause);
	}

	public CpfCnpjClienteJaCadastradoException(String message) {
		super(message);
	}

	public CpfCnpjClienteJaCadastradoException(Throwable cause) {
		super(cause);
	}

}
