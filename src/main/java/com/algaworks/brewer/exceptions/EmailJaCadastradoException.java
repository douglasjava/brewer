/**
 * 
 */
package com.algaworks.brewer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailJaCadastradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailJaCadastradoException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public EmailJaCadastradoException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public EmailJaCadastradoException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EmailJaCadastradoException(String message, Throwable cause) {
		super(message, cause);
	}

}
