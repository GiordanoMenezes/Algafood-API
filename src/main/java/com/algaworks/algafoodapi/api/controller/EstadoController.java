package com.algaworks.algafoodapi.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.repository.EstadoRepository;
import com.algaworks.algafoodapi.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepo;

	@Autowired
	private CadastroEstadoService cadEstadoService;

	@GetMapping
	public List<Estado> listar() {
		return estadoRepo.findAll();
	}

	@GetMapping("/{id}")
	public Estado buscar(@PathVariable Long id) {
		return cadEstadoService.buscarOuFalhar(id);
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Estado salvar(@RequestBody @Valid Estado estado) {
		return cadEstadoService.salvar(estado);
	}

	@PutMapping("/{id}")
	public Estado atualizar(@PathVariable Long id, @Valid @RequestBody Estado estado) {
		Estado estadoAtualizar = cadEstadoService.buscarOuFalhar(id);
		BeanUtils.copyProperties(estado, estadoAtualizar, "id");
		return cadEstadoService.salvar(estadoAtualizar);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadEstadoService.excluir(id);
	}

}
