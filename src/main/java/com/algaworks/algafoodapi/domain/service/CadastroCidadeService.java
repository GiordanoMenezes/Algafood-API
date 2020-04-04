package com.algaworks.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafoodapi.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	private static final String MSG_CIDADEEMUSO = "A cidade de id %d não pode ser excluído. Está em uso no sistema.";
	
	@Autowired
	private CidadeRepository cidadeRepo;
	
	@Autowired
	private CadastroEstadoService estadoService;

	public Cidade salvar(Cidade cidade) {
		
		Long estadoId = cidade.getEstado().getId();		
		Estado estado = estadoService.buscarOuFalhar(estadoId);
		cidade.setEstado(estado);
		
		return cidadeRepo.save(cidade);
	}
	
	public Cidade buscarOuFalhar(Long id) {
		return cidadeRepo.findById(id).orElseThrow(
				() -> new CidadeNaoEncontradaException(id));
	}

	public void excluir(Long id) {
		try {
			cidadeRepo.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new CidadeNaoEncontradaException(id);
		} catch (DataIntegrityViolationException dex) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CIDADEEMUSO, id));
		}
	}


}
