package com.algaworks.brewer.controller.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.Estilos;

public class EstiloConverter implements Converter<String, Estilo> {

	@Autowired
	private Estilos estilo;

	@Override
	public Estilo convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			return estilo.findById(Long.valueOf(codigo)).get();
		} else {
			return null;
		}
	}

}
