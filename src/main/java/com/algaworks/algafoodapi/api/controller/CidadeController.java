package com.algaworks.algafoodapi.api.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.api.assemblerDTO.CidadeAssembler;
import com.algaworks.algafoodapi.api.assemblerDTO.CidadeDisassembler;
import com.algaworks.algafoodapi.api.model.CidadeDTO;
import com.algaworks.algafoodapi.api.model.CidadeInputDTO;
import com.algaworks.algafoodapi.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafoodapi.domain.exception.NegocioException;
import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.repository.CidadeRepository;
import com.algaworks.algafoodapi.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepo;

	@Autowired
	private CadastroCidadeService cadCidadeService;

	@Autowired
	private CidadeAssembler cidadeAssembler;

	@Autowired
	private CidadeDisassembler cidadeDisassembler;

	@GetMapping
	public Collection<CidadeDTO> listar() {
		return cidadeAssembler.toCollectionDTO(cidadeRepo.findAll());
	}

	@GetMapping("/{id}")
	public CidadeDTO buscar(@PathVariable Long id) {
		return cidadeAssembler.toDTO(cadCidadeService.buscarOuFalhar(id));
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public CidadeDTO salvar(@RequestBody @Valid CidadeInputDTO cidadeInDTO) {
		try {
			Cidade cidadeSalvar = cidadeDisassembler.toDomain(cidadeInDTO);
			return cidadeAssembler.toDTO(cadCidadeService.salvar(cidadeSalvar));
		} catch (EstadoNaoEncontradoException enex) {
			throw new NegocioException(enex.getMessage());
		}
	}

	@PutMapping("/{id}")
	public CidadeDTO atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInputDTO cidadeInDTO) {

		try {
			Cidade cidadeAtualizar = cadCidadeService.buscarOuFalhar(id);
			cidadeAtualizar.setEstado(new Estado());

			cidadeDisassembler.updateObject(cidadeInDTO, cidadeAtualizar);
			return cidadeAssembler.toDTO(cadCidadeService.salvar(cidadeAtualizar));

		} catch (EstadoNaoEncontradoException enex) {
			throw new NegocioException(enex.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadCidadeService.excluir(id);
	}

}
