package com.algaworks.algafoodapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import com.algaworks.algafoodapi.domain.service.CadastroCozinhaService;

@CrossOrigin
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepo;

	@Autowired
	private CadastroCozinhaService cadCozinhaService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Cozinha> listar() {
		return cozinhaRepo.findAll();
	}

//	@GetMapping("/{id}")
//	public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
//		Optional<Cozinha> cozinha = cozinhaRepo.findById(id);
//
//		if (cozinha.isPresent()) {
//			return ResponseEntity.ok(cozinha.get());
//		}
//
//		return ResponseEntity.notFound().build();
//	}

	@GetMapping("/{id}")
	public Cozinha buscar(@PathVariable Long id) {
		return cadCozinhaService.buscarouFalhar(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
		return cadCozinhaService.salvar(cozinha);
	}

	@PutMapping("/{id}")
	public Cozinha atualizar(@PathVariable Long id, @RequestBody @Valid Cozinha cozinha) {

		Cozinha cozAtualizar = cadCozinhaService.buscarouFalhar(id);
		BeanUtils.copyProperties(cozinha, cozAtualizar, "id");
		return cadCozinhaService.salvar(cozAtualizar);
	}

//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> remover(@PathVariable Long id) {
//		try {
//			cadCozinhaService.excluir(id);
//			return ResponseEntity.noContent().build();
//
//		} catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//
//		} catch (EntidadeEmUsoException e) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
//		}
//	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadCozinhaService.excluir(id);
	}

}
