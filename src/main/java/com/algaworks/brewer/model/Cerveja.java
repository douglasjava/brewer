package com.algaworks.brewer.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cerveja")
public class Cerveja extends EntityBase {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "SKU é obrigatório")
	private String sku;

	@NotBlank(message = "nome é obrigatório")
	private String nome;

	@Size(min = 1, max = 50, message = "A descrição deve estar entre 1 e 50")
	private String descricao;

	private BigDecimal valor;

	@Column(name = "teor_alcoolico")
	private BigDecimal teorAlcoolico;

	private BigDecimal comissao;

	@Column(name = "quantidade_estoque")
	private Integer quatidadeEstoque;

	@Enumerated(EnumType.STRING)
	private Origem origem;

	@Enumerated(EnumType.STRING)
	private Sabor sabor;

	@ManyToOne
	@JoinColumn(name = "codigo_estilo")
	private Estilo estilo;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getTeorAlcoolico() {
		return teorAlcoolico;
	}

	public void setTeorAlcoolico(BigDecimal teorAlcoolico) {
		this.teorAlcoolico = teorAlcoolico;
	}

	public BigDecimal getComissao() {
		return comissao;
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}

	public Integer getQuatidadeEstoque() {
		return quatidadeEstoque;
	}

	public void setQuatidadeEstoque(Integer quatidadeEstoque) {
		this.quatidadeEstoque = quatidadeEstoque;
	}

	public Origem getOrigem() {
		return origem;
	}

	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

	public Sabor getSabor() {
		return sabor;
	}

	public void setSabor(Sabor sabor) {
		this.sabor = sabor;
	}

	public Estilo getEstilo() {
		return estilo;
	}

	public void setEstilo(Estilo estilo) {
		this.estilo = estilo;
	}

}