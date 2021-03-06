package com.algaworks.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Grupo;

public class GrupoConverter implements Converter<String, Grupo> {

	@Override
	public Grupo convert(String codigo) {
		Grupo grupo = new Grupo();
		if (!StringUtils.isEmpty(codigo)) {
			grupo.setCodigo(Long.valueOf(codigo));
			return grupo;
		} else {
			return grupo;
		}
	}

}
