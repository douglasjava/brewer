package com.algaworks.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.exceptions.ImpossivelExcluirEntidadeException;
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

	@Transactional
	public void excluir(Estilo estilo) {
		try {
			this.estilos.delete(estilo);
			this.estilos.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar estilo. Já está atrelado a alguma cerveja.");
		}
	}

}
