package com.algaworks.brewer.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "estilo")
public class Estilo extends EntityBase {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Nome é obrigatório")
	private String nome;

	@OneToMany(mappedBy = "estilo")
	private List<Cerveja> cervejas;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Cerveja> getCervejas() {
		return cervejas;
	}

	public void setCervejas(List<Cerveja> cervejas) {
		this.cervejas = cervejas;
	}

	public boolean isNovo() {
		return super.getCodigo() == null;
	}
}
