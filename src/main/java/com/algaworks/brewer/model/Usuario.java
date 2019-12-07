package com.algaworks.brewer.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.algaworks.brewer.validation.AtributoConfirmacao;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "codigo")
@AtributoConfirmacao(atributo = "senha", atributoConfirmacao = "confirmacaoSenha", message = "Confirmação da senha não confere.")
@Entity
@Table(name = "usuario")
@DynamicUpdate
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotBlank(message = "Nome é obrigatório")
	private String nome;

	@NotBlank(message = "E-mail é obrigatório")
	@Email(message = "E-mail inválido")
	private String email;

	private String senha;

	@Transient
	private String confirmacaoSenha;

	private boolean ativo;

	@PreUpdate
	private void preUpdate() {
		this.confirmacaoSenha = senha;
	}

	@Size(min = 1, message = "Selecione pelo menos um grupo")
	@ManyToMany
	@JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "codigo_usuario"), inverseJoinColumns = @JoinColumn(name = "codigo_grupo"))
	private List<Grupo> grupos;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;

	public boolean isNovo() {
		return Objects.isNull(this.codigo);
	}
	
	public boolean isEdicao() {
		return Objects.nonNull(this.codigo);
	}


}
