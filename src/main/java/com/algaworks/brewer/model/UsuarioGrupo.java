package com.algaworks.brewer.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "usuario_grupo")
public class UsuarioGrupo implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsuarioGrupoId id;

}
