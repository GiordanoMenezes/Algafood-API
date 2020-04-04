package com.algaworks.algafoodapi.api.controller;

import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.NegocioException;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import com.algaworks.algafoodapi.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restRepo;

	@Autowired
	private CadastroRestauranteService cadRestService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<Restaurante> listar() {
		return restRepo.listAll();
	}

	@GetMapping("/{id}")
	public Restaurante buscar(@PathVariable Long id) {
       return cadRestService.buscarOuFalhar(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody Restaurante restaurante) {
		try {
		return cadRestService.salvar(restaurante);
		} catch (CozinhaNaoEncontradaException enex) {
			throw new NegocioException(enex.getMessage());
		}
	}

	@PutMapping("/{id}")
	public Restaurante atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {

		Restaurante restAtualizar = cadRestService.buscarOuFalhar(id);
		BeanUtils.copyProperties(restaurante, restAtualizar, "id", "formasPagamento", "endereco", "dataCadastro",
				"produtos");
		
     try {
		return cadRestService.salvar(restAtualizar);
     } catch (CozinhaNaoEncontradaException enex) {
    	 throw new NegocioException(enex.getMessage());
     }
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadRestService.excluir(id);
	}

}
