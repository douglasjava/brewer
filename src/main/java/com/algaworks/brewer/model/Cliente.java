package com.algaworks.brewer.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import com.algaworks.brewer.model.validation.ClienteGroupSequenceProvider;
import com.algaworks.brewer.model.validation.group.CnpjGroup;
import com.algaworks.brewer.model.validation.group.CpfGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Marques
 *
 */
@Entity
@Table(name = "cliente")
@Getter
@Setter
@EqualsAndHashCode(of = "codigo")
@GroupSequenceProvider(ClienteGroupSequenceProvider.class)
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotBlank(message = "Nome é obrigatório")
	private String nome;

	@NotNull(message = "Tipo pessoa é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_pessoa")
	private TipoPessoa tipoPessoa;

	@NotBlank(message = "CPF/CNPJ é obrigatório")
	@CPF(groups = CpfGroup.class)
	@CNPJ(groups = CnpjGroup.class)
	@Column(name = "cpf_cnpj")
	private String cpfOuCnpj;

	private String telefone;

	@Email(message = "E-mail inválido")
	private String email;

	@Embedded
	@JsonIgnore
	private Endereco endereco;

	@PrePersist
	@PreUpdate
	private void preInsertPreUpdate() {
		this.cpfOuCnpj = TipoPessoa.removerFormatacao(this.cpfOuCnpj);
	}
	
	@PostLoad
	private void postLoad() {
		this.cpfOuCnpj = this.tipoPessoa.formatar(this.cpfOuCnpj);
	}

	public String getCpfOuCnpjSemFormatacao() {
		return TipoPessoa.removerFormatacao(this.cpfOuCnpj);
	}
	
	public boolean isNovo() {
		return this.getCodigo() == null;
	}

}
