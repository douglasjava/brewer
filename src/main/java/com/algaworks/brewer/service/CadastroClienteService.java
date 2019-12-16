package com.algaworks.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.exceptions.CpfCnpjClienteJaCadastradoException;
import com.algaworks.brewer.exceptions.ImpossivelExcluirEntidadeException;
import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.repository.Cidades;
import com.algaworks.brewer.repository.Clientes;

@Service
public class CadastroClienteService {

	@Autowired
	private Clientes clientes;
	
	@Autowired
	private Cidades cidades;

	@Transactional
	public void salvar(Cliente entity) {
		
		if (entity.isNovo()) {
			
			Optional<Cliente> clienteExistente = clientes.findByCpfOuCnpj(entity.getCpfOuCnpjSemFormatacao());	
			if(clienteExistente.isPresent()) {
				throw new CpfCnpjClienteJaCadastradoException("CPF/CNPJ já cadastrado.");
			}					
		}
		
		clientes.save(entity);
	}

	@Transactional(readOnly = true)
	public void comporDadosEndereco(Cliente cliente) {
		if (cliente.getEndereco() == null 
				|| cliente.getEndereco().getCidade() == null
				|| cliente.getEndereco().getCidade().getCodigo() == null) {
			return;
		}

		Cidade cidade = this.cidades.findByCodigoFetchingEstado(cliente.getEndereco().getCidade().getCodigo());

		cliente.getEndereco().setCidade(cidade);
		cliente.getEndereco().setEstado(cidade.getEstado());
	}

	@Transactional
	public void excluir(Cliente cliente) {
		try {
			this.clientes.delete(cliente);
			this.clientes.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar cliente. Já está atrelado a alguma venda.");
		}
	}
	
}
