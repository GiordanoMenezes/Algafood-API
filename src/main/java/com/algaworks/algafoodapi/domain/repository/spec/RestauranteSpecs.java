package com.algaworks.algafoodapi.domain.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafoodapi.domain.model.Restaurante;

public class RestauranteSpecs {

	public static Specification<Restaurante> comFreteGratis() {

		return (root, query, cb) -> cb.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}

	public static Specification<Restaurante> comNomeContem(String nome) {

		return (root, query, cb) -> cb.like(root.get("nome"), "%" + nome + "%");
	}

}
