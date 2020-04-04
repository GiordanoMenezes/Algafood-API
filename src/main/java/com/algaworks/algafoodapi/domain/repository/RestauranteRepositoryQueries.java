package com.algaworks.algafoodapi.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.algafoodapi.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> find(String nome, BigDecimal taxaIni, BigDecimal taxaFim);
	
	List<Restaurante> porNomeFreteGratis(String nome);

}