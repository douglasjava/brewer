package com.algaworks.brewer.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SegurancaController {

	@GetMapping("/brewer/login")
	public String login(@AuthenticationPrincipal User user) {
		if (user != null) {
			return "redirect:/brewer/cervejas";
		}

		return "Login";
	}

	@GetMapping("/brewer/403")
	public String acessoNegado() {
		return "403";
	}

}
