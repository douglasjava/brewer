package com.algaworks.brewer.dto;

import com.algaworks.brewer.service.StatusUsuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtivarUsuario {

	private Long[] codigos;
	private StatusUsuario status;

}
