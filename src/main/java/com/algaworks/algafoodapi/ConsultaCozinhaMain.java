package com.algaworks.algafoodapi;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;

@SpringBootApplication
public class ConsultaCozinhaMain {

	public static void main(String[] args) {
		
		ApplicationContext context = new SpringApplicationBuilder(ConsultaCozinhaMain.class).
				web(WebApplicationType.NONE).run(args);
		
		CozinhaRepository cadastroCozinha = context.getBean(CozinhaRepository.class);
		
		List<Cozinha> cozinhas = cadastroCozinha.findAll();
		
		for	(Cozinha cozinha: cozinhas) {
		   System.out.println(cozinha.getNome());	
		}
	}
}
