package com.algaworks.brewer.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;

	private String logradouro;

	private String numero;

	private String complemento;

	private String cep;

	@ManyToOne
	@JoinColumn(name = "codigo_cidade")
	private Cidade cidade;

	@Transient
	private Estado estado;

	public String getNomeCidadeSiglaEstado() {
		return Objects.nonNull(this.cidade) ? this.cidade.getNome() + "/" + this.cidade.getEstado().getSigla() : "";
	}

}
