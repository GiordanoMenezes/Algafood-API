package com.algaworks.algafoodapi.api.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.api.assemblerDTO.RestauranteAssembler;
import com.algaworks.algafoodapi.api.assemblerDTO.RestauranteDisassembler;
import com.algaworks.algafoodapi.api.model.RestauranteDTO;
import com.algaworks.algafoodapi.api.model.RestauranteInputDTO;
import com.algaworks.algafoodapi.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.NegocioException;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import com.algaworks.algafoodapi.domain.service.CadastroRestauranteService;

@CrossOrigin
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restRepo;

	@Autowired
	private CadastroRestauranteService cadRestService;

	@Autowired
	private RestauranteAssembler restauranteAssembler;

	@Autowired
	private RestauranteDisassembler restauranteDisassembler;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<RestauranteDTO> listar() {
		return restauranteAssembler.toCollectionDTO(restRepo.listAll());
	}

	@GetMapping("/{id}")
	public RestauranteDTO buscar(@PathVariable Long id) {
		return restauranteAssembler.toDTO(cadRestService.buscarOuFalhar(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInputDTO restauranteInDTO) {
		try {
			Restaurante restSalvar = restauranteDisassembler.toDomain(restauranteInDTO);
			return restauranteAssembler.toDTO(cadRestService.salvar(restSalvar));
		} catch (CozinhaNaoEncontradaException enex) {
			throw new NegocioException(enex.getMessage());
		}
	}

	@PutMapping("/{id}")
	public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInputDTO restauranteInDTO) {

		try {
			Restaurante restAtualizar = cadRestService.buscarOuFalhar(id);

			// Quando alteramos o id da cozinha, passando um novo id de cozinha em
			// restInDTO, o JPA entenderá que estamos querendo substituir
			// uma chave primária de uma entidade existente, e gerará um erro. Pra evitar
			// isso, instanciamos uma nova cozinha.
			// Como cozinha é obrigatório na atualização, não há perigo em deletar o
			// registro de cozinha, caso o usuario deixasse cozinha sem informar.
			restAtualizar.setCozinha(new Cozinha());

			restauranteDisassembler.updateObject(restauranteInDTO, restAtualizar);

			return restauranteAssembler.toDTO(cadRestService.salvar(restAtualizar));
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
