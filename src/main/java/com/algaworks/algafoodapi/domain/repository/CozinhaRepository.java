package com.algaworks.algafoodapi.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.algaworks.algafoodapi.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

	List<Cozinha> findTodasByNomeContaining(String nome);

	Cozinha findUnicaByNome(String nome);

	boolean existsByNome(String nome);
}
