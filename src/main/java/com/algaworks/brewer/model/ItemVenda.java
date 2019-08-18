package com.algaworks.brewer.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "item_venda")
public class ItemVenda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	private Integer quantidade;

	@Column(name = "valor_unitario")
	private BigDecimal valorUnitario;

	@ManyToOne
	@JoinColumn(name = "codigo_cerveja")
	private Cerveja cerveja;

	@ManyToOne
	@JoinColumn(name = "codigo_venda")
	private Venda venda;

	public BigDecimal getValorTotal() {
		return valorUnitario.multiply(new BigDecimal(quantidade));
	}
}
