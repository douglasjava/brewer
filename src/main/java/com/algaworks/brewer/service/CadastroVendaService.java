package com.algaworks.brewer.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.StatusVenda;
import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.Vendas;

@Service
public class CadastroVendaService {

	@Autowired
	private Vendas vendas;

	@Transactional
	public Venda salvar(Venda venda) {
		if(venda.isSalvarProibido()) {
			throw new RuntimeException("Usuário tentando salvar uma venda proibida");
		}
		
		if (venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
		} else {
			Optional<Venda> vendaExistenteOptional = findById(venda);
			Venda vendaExistente = vendaExistenteOptional.isPresent() ? vendaExistenteOptional.get() : new Venda();
			venda.setDataCriacao(vendaExistente.getDataCriacao());
		}

		if (venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(),
					venda.getHorarioEntrega() != null ? venda.getHorarioEntrega() : LocalTime.NOON));
		}

		return vendas.saveAndFlush(venda);
	}

	private Optional<Venda> findById(Venda venda) {
		Optional<Venda> vendaExistenteOptional = vendas.findById(venda.getCodigo());
		return vendaExistenteOptional;
	}

	@PreAuthorize("#venda.usuario == principal.usuario or hasRole('CANCELAR_VENDA')")
	@Transactional
	public void cancelar(Venda venda) {
		Optional<Venda> vendaExistenteOptional = findById(venda);

		if (vendaExistenteOptional.isPresent()) {
			Venda vendaExistente = vendaExistenteOptional.get();
			vendaExistente.setStatus(StatusVenda.CANCELADA);
			vendas.save(vendaExistente);
		}

	}

	@Transactional
	public void emitir(Venda venda) {
		venda.setStatus(StatusVenda.EMITIDA);
		salvar(venda);
	}
}
