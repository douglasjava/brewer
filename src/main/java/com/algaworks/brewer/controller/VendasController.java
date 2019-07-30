package com.algaworks.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/brewer/vendas")
public class VendasController {

	@GetMapping("/nova")
	public String novo() {
		return "venda/CadastroVenda";
	}

}
