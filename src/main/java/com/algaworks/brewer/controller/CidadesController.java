package com.algaworks.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.algaworks.brewer.model.Cerveja;

@Controller
@RequestMapping("/brewer")
public class CidadesController {

	@RequestMapping("/cidades/novo")
	public String novo(Cerveja cerveja) {
		return "cidade/CadastroCidade";
	}

}
