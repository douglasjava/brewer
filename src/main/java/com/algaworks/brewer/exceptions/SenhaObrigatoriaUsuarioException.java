/**
 * 
 */
package com.algaworks.brewer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Marques
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SenhaObrigatoriaUsuarioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SenhaObrigatoriaUsuarioException() {
	}

	/**
	 * @param message
	 */
	public SenhaObrigatoriaUsuarioException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SenhaObrigatoriaUsuarioException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SenhaObrigatoriaUsuarioException(String message, Throwable cause) {
		super(message, cause);
	}

}
