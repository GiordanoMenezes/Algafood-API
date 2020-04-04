package com.algaworks.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafoodapi.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String MSG_COZINHA_EM_USO = "A cozinha de id %d não pode ser excluída. Está em uso no sistema.";
	@Autowired
	private CozinhaRepository cozinhaRepo;

	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepo.save(cozinha);
	}

	public Cozinha buscarouFalhar(Long id) {
		return cozinhaRepo.findById(id).orElseThrow(
				() -> new CozinhaNaoEncontradaException(id));
	}

	public void excluir(Long id) {
		try {
			cozinhaRepo.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new CozinhaNaoEncontradaException(id);
		} catch (DataIntegrityViolationException dex) {
			throw new EntidadeEmUsoException(
					String.format(MSG_COZINHA_EM_USO, id));
		}
	}

}
