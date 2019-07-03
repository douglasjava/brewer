package com.algaworks.brewer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CepDTO {

	private String bairro;
	private String cidade;
	private String logradouro;
	private EstadoInfo estado_info;
	private String cep;
	private String estado;
}
