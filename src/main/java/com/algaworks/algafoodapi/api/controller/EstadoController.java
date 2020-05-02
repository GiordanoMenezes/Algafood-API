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

import com.algaworks.algafoodapi.api.assemblerDTO.EstadoAssembler;
import com.algaworks.algafoodapi.api.assemblerDTO.EstadoDisassembler;
import com.algaworks.algafoodapi.api.model.EstadoDTO;
import com.algaworks.algafoodapi.api.model.EstadoInputDTO;
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
	
	@Autowired
	private EstadoAssembler estadoAssembler;
	
	@Autowired
	private EstadoDisassembler estadoDisassembler;

	@GetMapping
	public Collection<EstadoDTO> listar() {
		return estadoAssembler.toCollectionDTO(estadoRepo.findAll());
	}

	@GetMapping("/{id}")
	public EstadoDTO buscar(@PathVariable Long id) {
		return estadoAssembler.toDTO(cadEstadoService.buscarOuFalhar(id));
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public EstadoDTO salvar(@RequestBody @Valid EstadoInputDTO estadoInDTO) {
			Estado estadoSalvar = estadoDisassembler.toDomain(estadoInDTO);
			return estadoAssembler.toDTO(cadEstadoService.salvar(estadoSalvar));
		
	}

	@PutMapping("/{id}")
	public EstadoDTO atualizar(@PathVariable Long id, @Valid @RequestBody EstadoInputDTO estadoInDTO) {
		Estado estadoAtualizar = cadEstadoService.buscarOuFalhar(id);
		estadoDisassembler.updateObject(estadoInDTO, estadoAtualizar);
		return estadoAssembler.toDTO(cadEstadoService.salvar(estadoAtualizar));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadEstadoService.excluir(id);
	}

}
