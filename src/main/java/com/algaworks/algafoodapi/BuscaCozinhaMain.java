package com.algaworks.algafoodapi;


import java.util.Optional;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;

@SpringBootApplication
public class BuscaCozinhaMain {

	public static void main(String[] args) {
		
		ApplicationContext context = new SpringApplicationBuilder(BuscaCozinhaMain.class).
				web(WebApplicationType.NONE).run(args);
		
		CozinhaRepository cadastroCozinha = context.getBean(CozinhaRepository.class);
		
		Optional<Cozinha> cozinha = cadastroCozinha.findById(1L);
		
		System.out.println(cozinha.get().getNome());

	}
}
