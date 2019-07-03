package com.algaworks.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.exceptions.CpfCnpjClienteJaCadastradoException;
import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.repository.Clientes;

@Service
public class CadastroClienteService {

	@Autowired
	private Clientes clientes;

	@Transactional
	public void salvar(Cliente entity) {
		
		Optional<Cliente> clienteExistente = clientes.findByCpfOuCnpj(entity.getCpfOuCnpjSemFormatacao());	
		if(clienteExistente.isPresent()) {
			throw new CpfCnpjClienteJaCadastradoException("CPF/CNPJ já cadastrado.");
		}		
		clientes.save(entity);
	}

}
