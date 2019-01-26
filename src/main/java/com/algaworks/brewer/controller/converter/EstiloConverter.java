package com.algaworks.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Estilo;

public class EstiloConverter implements Converter<String, Estilo> {

	@Override
	public Estilo convert(String codigo) {
		Estilo estilo = new Estilo();
		if (!StringUtils.isEmpty(codigo)) {
			estilo.setCodigo(Long.valueOf(codigo));
			return estilo;
		} else {
			return estilo;
		}
	}

}
