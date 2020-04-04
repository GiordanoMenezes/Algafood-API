package com.algaworks.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	private static final String MSG_ESTADOEMUSO = "O estado de id %d não pode ser excluído. Está em uso no sistema.";
	
	@Autowired
	private EstadoRepository estadoRepo;

	public Estado salvar(Estado estado) {
		return estadoRepo.save(estado);
	}
	
	public Estado buscarOuFalhar(Long id) {
		return estadoRepo.findById(id).orElseThrow(
				() -> new EstadoNaoEncontradoException(id));
	}

	public void excluir(Long id) {
		try {
			estadoRepo.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new EstadoNaoEncontradoException(id);
		} catch (DataIntegrityViolationException dex) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADOEMUSO, id));
		}
	}


}
