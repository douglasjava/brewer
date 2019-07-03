package com.algaworks.brewer.controller.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.algaworks.brewer.exceptions.CepException;
import com.algaworks.brewer.exceptions.NomeEstiloJaCadastradoException;

@ControllerAdvice
public class ControllerAdviceExceptionHandler {

	@ExceptionHandler(NomeEstiloJaCadastradoException.class)
	public ResponseEntity<String> handleNomeEstiloJaCadastradoException(NomeEstiloJaCadastradoException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(CepException.class)
	public ResponseEntity<String> handleCepException(CepException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
