package com.algaworks.brewer.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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
@Entity
@Table(name = "grupo")
public class Grupo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	private String nome;

	@ManyToMany
	@JoinTable(name = "grupo_permissao", joinColumns = @JoinColumn(name = "codigo_grupo"), inverseJoinColumns = @JoinColumn(name = "codigo_permissao"))
	private List<Permissao> permissoes;

}
