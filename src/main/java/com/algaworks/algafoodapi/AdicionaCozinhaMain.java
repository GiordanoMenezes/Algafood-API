package com.algaworks.algafoodapi;


import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;

@SpringBootApplication
public class AdicionaCozinhaMain {

	public static void main(String[] args) {
		
		ApplicationContext context = new SpringApplicationBuilder(AdicionaCozinhaMain.class).
				web(WebApplicationType.NONE).run(args);
		
		CozinhaRepository cadastroCozinha = context.getBean(CozinhaRepository.class);
		
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Nordestina");
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Árabe");
		
		cadastroCozinha.save(cozinha1);
		cadastroCozinha.save(cozinha2);

	}
}
