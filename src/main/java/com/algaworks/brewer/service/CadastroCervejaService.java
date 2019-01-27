package com.algaworks.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.Cervejas;

@Service
public class CadastroCervejaService extends ServiceBase {

	@Autowired
	private Cervejas cervejas;

	public void salvar(Cerveja cerveja) {
		adicionarData(cerveja);
		cervejas.save(cerveja);
	}

}
