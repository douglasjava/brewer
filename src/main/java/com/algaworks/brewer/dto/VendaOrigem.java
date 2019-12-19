package com.algaworks.brewer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VendaOrigem {

	private String mes;
	private Integer totalNacional;
	private Integer totalInternacional;

}
