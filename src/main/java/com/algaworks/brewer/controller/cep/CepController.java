package com.algaworks.brewer.controller.cep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.brewer.dto.CepDTO;
import com.algaworks.brewer.service.CepService;

@RestController
@RequestMapping("/brewer/cep")
public class CepController {

	@Autowired
	private CepService service;

	@PostMapping
	public CepDTO buscaCep(@RequestBody String cep) {
		return service.buscaCep(cep);
	}

}
