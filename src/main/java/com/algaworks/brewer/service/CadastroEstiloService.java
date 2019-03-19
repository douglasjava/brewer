package com.algaworks.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.brewer.exceptions.NomeEstiloJaCadastradoException;
import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.Estilos;

@Service
public class CadastroEstiloService extends ServiceBase {

	@Autowired
	private Estilos estilos;

	public void salvar(Estilo estilo) {

		executeRules(estilo);

		adicionarData(estilo);
		estilos.saveAndFlush(estilo);
	}

	private void executeRules(Estilo estilo) {
		validarEstiloJaCadastrado(estilo);
	}

	private void validarEstiloJaCadastrado(Estilo estilo) {
		Optional<Estilo> estiloOptional = estilos.findByNomeIgnoreCase(estilo.getNome());
		if (estiloOptional.isPresent()) {
			throw new NomeEstiloJaCadastradoException("Nome do estilo já cadastrado");
		}
	}

}
