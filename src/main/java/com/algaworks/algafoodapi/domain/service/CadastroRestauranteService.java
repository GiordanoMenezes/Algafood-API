package com.algaworks.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_RESTAURANTEEMUSO = "O restaurante de Id %d não pode ser excluído. Está em uso pelo sistema.";


	@Autowired
	private RestauranteRepository restRepo;

	@Autowired
	private CadastroCozinhaService cozinhaService;

	public Restaurante salvar(Restaurante restaurante) {
		
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaService.buscarouFalhar(cozinhaId);	
		restaurante.setCozinha(cozinha);
		
		return restRepo.save(restaurante);
	}
	
	public Restaurante buscarOuFalhar(Long id) {
		return restRepo.findById(id)
		.orElseThrow(() -> new RestauranteNaoEncontradoException(id));
	}

	
	public void excluir(Long id) {
		try {
		   restRepo.deleteById(id);
		}catch (EmptyResultDataAccessException ex) {
			throw new RestauranteNaoEncontradoException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTEEMUSO, id));
		}
	}

}
