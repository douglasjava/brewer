package com.algaworks.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.Estilos;

@Service
public class CadastroEstiloService extends ServiceBase {

	@Autowired
	private Estilos estilos;

	public void salvar(Estilo estilo) {
		adicionarData(estilo);
		estilos.save(estilo);
	}

}
