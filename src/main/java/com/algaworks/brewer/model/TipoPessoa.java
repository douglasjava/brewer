package com.algaworks.brewer.model;

public enum TipoPessoa {

	F("Física"), J("Jurídica");

	private String descricao;

	private TipoPessoa(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
