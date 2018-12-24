package com.algaworks.brewer.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "estilo")
public class Estilo extends EntityBase {

	private static final long serialVersionUID = 1L;

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

	@Override
	public String toString() {
		return "Estilo [nome=" + nome + "]";
	}

}
