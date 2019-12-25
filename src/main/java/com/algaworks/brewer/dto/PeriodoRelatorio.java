package com.algaworks.brewer.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeriodoRelatorio {

	private LocalDate dataInicio;
	private LocalDate dataFim;

}
