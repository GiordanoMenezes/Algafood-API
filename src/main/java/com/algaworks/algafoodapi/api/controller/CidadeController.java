package com.algaworks.algafoodapi.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
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

import com.algaworks.algafoodapi.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafoodapi.domain.exception.NegocioException;
import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.repository.CidadeRepository;
import com.algaworks.algafoodapi.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepo;

	@Autowired
	private CadastroCidadeService cadCidadeService;

	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepo.findAll();
	}

	@GetMapping("/{id}")
	public Cidade buscar(@PathVariable Long id) {
		return cadCidadeService.buscarOuFalhar(id);
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Cidade salvar(@RequestBody Cidade cidade) {
		return cadCidadeService.salvar(cidade);
	}

	@PutMapping("/{id}")
	public Cidade atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
	
		Cidade cidadeAtualizar = cadCidadeService.buscarOuFalhar(id);
		BeanUtils.copyProperties(cidade, cidadeAtualizar, "id");
		try {
			return cadCidadeService.salvar(cidadeAtualizar);
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
