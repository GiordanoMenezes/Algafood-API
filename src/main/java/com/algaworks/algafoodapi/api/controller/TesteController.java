package com.algaworks.algafoodapi.api.controller;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/testes")
public class TesteController {

	@Autowired
	private CozinhaRepository cozRepo;

	@Autowired
	private RestauranteRepository restRepo;

	@GetMapping("/cozinhas/por-nome-contendo")
	public List<Cozinha> cozinhasPorNome(@RequestParam("nome") String nome) {
		return cozRepo.findTodasByNomeContaining(nome);
	}

	@GetMapping("/cozinhas/unica-por-nome")
	public Cozinha unicaPorNome(@RequestParam("nome") String nome) {
		return cozRepo.findUnicaByNome(nome);
	}

	@GetMapping("/cozinhas/exists")
	public boolean cozinhaExists(String nome) {
		return cozRepo.existsByNome(nome);
	}

	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> restaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restRepo.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}

	@GetMapping("/restaurantes/por-nome-cozinha")
	public List<Restaurante> restaurantesPorNomeENomeCozinha(@RequestParam(defaultValue = "") String nome,
			@RequestParam(defaultValue = "") Long cozinha) {
		return restRepo.consultarPorNome(nome, cozinha);
	}

	@GetMapping("/restaurantes/primeiro-por-nome")
	public Optional<Restaurante> restaurantePrimeiroPorNome(String nome) {
		return restRepo.findFirstRestauranteByNomeContaining(nome);
	}

	@GetMapping("/restaurantes/top2-por-nome")
	public List<Restaurante> restaurantesTop2PorNome(String nome) {
		return restRepo.findTop2ByNomeContaining(nome);
	}

	@GetMapping("/restaurantes/count-por-cozinha")
	public int restaurantesCountPorCozinha(Long cozinhaId) {
		return restRepo.countByCozinhaId(cozinhaId);
	}

	@GetMapping("/restaurantes/por-nome-taxafrete")
	public List<Restaurante> restaurantesPorNomeETaxaFrete(String nome, BigDecimal taxaIni, BigDecimal taxaFim) {
		return restRepo.find(nome, taxaIni, taxaFim);
	}

	@GetMapping("/restaurantes/por-nome-fretegratis")
	public List<Restaurante> restaurantesPorNomeETFreteGratis(String nome) {

       return restRepo.porNomeFreteGratis(nome);

	}
	
	@GetMapping("/restaurantes/primeiro")
	public Optional<Restaurante> restaurantePrimeiro() {
		
		return restRepo.buscarPrimeiro();
	}
	
	@GetMapping("/cozinhas/primeiro")
	public Optional<Cozinha> cozinhaPrimeiro() {
		
		return cozRepo.buscarPrimeiro();
	}
}
