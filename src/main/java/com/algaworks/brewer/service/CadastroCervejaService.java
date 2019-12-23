package com.algaworks.brewer.service;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.exceptions.ImpossivelExcluirEntidadeException;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.storage.FotoStorage;

@Service
public class CadastroCervejaService extends ServiceBase {

	@Autowired
	private Cervejas cervejas;

	@Autowired
	private FotoStorage fotoStorage;

	public void salvar(Cerveja cerveja) {
		adicionarData(cerveja);
		cervejas.save(cerveja);
	}

	@Transactional
	public void excluir(Cerveja cerveja) throws ImpossivelExcluirEntidadeException {
		try {
			String foto = cerveja.getFoto();
			cervejas.delete(cerveja);
			cervejas.flush();
			if (!StringUtils.isEmpty(foto)) {
				fotoStorage.excluir(foto);
			}
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar cerveja. Já foi usada em alguma venda.");
		}
	}

}
