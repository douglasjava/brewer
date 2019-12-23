package com.algaworks.brewer.dto;

import static org.springframework.util.StringUtils.isEmpty;

import java.math.BigDecimal;

import com.algaworks.brewer.model.Origem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CervejaDTO {

	private Long codigo;
	private String sku;
	private String nome;
	private String origem;
	private BigDecimal valor;
	private String foto;
	private String urlThumbnailFoto;

	public CervejaDTO(Long codigo, String sku, String nome, Origem origem, BigDecimal valor, String foto) {
		super();
		this.codigo = codigo;
		this.sku = sku;
		this.nome = nome;
		this.origem = origem.getDescricao();
		this.valor = valor;
		this.foto = isEmpty(foto) ? "cerveja-mock.png" : foto;
	}

}
