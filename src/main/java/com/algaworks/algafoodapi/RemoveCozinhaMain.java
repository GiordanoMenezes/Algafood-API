package com.algaworks.algafoodapi;


import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafoodapi.domain.service.CadastroCozinhaService;

@SpringBootApplication
public class RemoveCozinhaMain {

	public static void main(String[] args) {
		
		ApplicationContext context = new SpringApplicationBuilder(RemoveCozinhaMain.class).
				web(WebApplicationType.NONE).run(args);
		
		CadastroCozinhaService cadastroCozinha = context.getBean(CadastroCozinhaService.class);
		
        cadastroCozinha.excluir(1L);

	}
}
