package com.algaworks.brewer.model;

public enum Sabor {

	ADOCICADA("Adocicada"), 
	AMARGA("amarga"), 
	FORTE("forte"), 
	FRUTADA("frutada"), 
	SUAVE("suave");

	private String descricao;

	private Sabor(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
