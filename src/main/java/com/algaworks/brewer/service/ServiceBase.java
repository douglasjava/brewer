package com.algaworks.brewer.service;

import java.util.Date;

import com.algaworks.brewer.model.EntityBase;

public class ServiceBase {

	public void adicionarData(EntityBase entity) {
		entity.setDataCriacao(new Date());
	}

}
