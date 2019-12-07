package com.algaworks.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.dto.AtivarUsuario;
import com.algaworks.brewer.exceptions.EmailJaCadastradoException;
import com.algaworks.brewer.exceptions.SenhaObrigatoriaUsuarioException;
import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.Grupos;
import com.algaworks.brewer.repository.Usuarios;
import com.algaworks.brewer.repository.filter.UsuarioFilter;
import com.algaworks.brewer.service.CadastroUsuarioService;

@Controller
@RequestMapping("/brewer/usuarios")
public class UsuariosController {

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;

	@Autowired
	private Grupos grupos;
	
	@Autowired
	private Usuarios usuarios;

	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		mv.addObject("grupos", grupos.findAll());
		return mv;
	}

	@PostMapping({ "/novo", "{\\+d}" })
	public ModelAndView cadastrar(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return novo(usuario);
		}

		try {
			cadastroUsuarioService.salvar(usuario);
		} catch (EmailJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);
		} catch (SenhaObrigatoriaUsuarioException e) {
			result.rejectValue("senha", e.getMessage(), e.getMessage());
			return novo(usuario);
		}

		attributes.addFlashAttribute("mensagem", "Usuário realizado com sucesso!");
		return new ModelAndView("redirect:/brewer/usuarios/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter, BindingResult result,
			@PageableDefault(size = 3) Pageable pageable, HttpServletRequest httpServletRequest) {
		
		ModelAndView mv = new ModelAndView("/usuario/PesquisaUsuarios");
		mv.addObject("grupos", grupos.findAll());
		
		PageWrapper<Usuario> pagina = new PageWrapper<>(usuarios.filtrar(usuarioFilter, pageable), httpServletRequest);
		mv.addObject("pagina", pagina);
		
		return mv;
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestBody AtivarUsuario ativarUsuario) {
		cadastroUsuarioService.alterarStatus(ativarUsuario.getCodigos(), ativarUsuario.getStatus());
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Usuario usuario = usuarios.buscarComGrupos(codigo);
		ModelAndView mv = novo(usuario);
		mv.addObject(usuario);
		return mv;
	}

}
