package com.algaworks.brewer.model;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ItemVenda {

	private Long codigo;
	private Integer quantidade;
	private BigDecimal valorUnitario;
	private Cerveja cerveja;

	public BigDecimal getValorTotal() {
		return valorUnitario.multiply(new BigDecimal(quantidade));
	}
}
