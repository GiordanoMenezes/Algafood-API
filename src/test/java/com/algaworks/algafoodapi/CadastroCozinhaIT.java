package com.algaworks.algafoodapi;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = AlgafoodApiApplication.class)
public class CadastroCozinhaIT {
	
	// TESTES DE API
	
	@LocalServerPort
	private int port;
	
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
	}
	
	//valição de Status Code
	@Test
	public void deveRetornar200_QuandoListarCozinhas() {
		
		RestAssured
		.given()
		    .accept(ContentType.JSON)
		.when()
		    .get()
	    .then()
	        .statusCode(200);
	}
	
	//validacao de body
	@Test
	public void deveConterCozinhasComNomesIndianaeTailandesa_QuandoListarCozinhas() {
		
		
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("nome", Matchers.hasItems("Nordestina","Tailandesa"));
	}
	
	
	
	// EXEMPLO DE TESTES DE INTEGRAÇÃO - NÃO USAREMOS NO PROJETO

//	@Autowired
//	private CadastroCozinhaService cozinhaService;
//	
//	@Test
//	public void deveAtribuirId_QuandoCadastrarCozinha() {
//		// cenário
//		Cozinha novaCozinha = new Cozinha();
//		novaCozinha.setNome("Chinesa");
//		
//		// ação
//		novaCozinha = cozinhaService.salvar(novaCozinha);
//		
//		// validação
//		assertThat(novaCozinha).isNotNull();
//		assertThat(novaCozinha.getId()).isNotNull();
//	}
//	
//	@Test(expected = ConstraintViolationException.class)
//	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
//		Cozinha novaCozinha = new Cozinha();
//		novaCozinha.setNome(null);
//		
//		novaCozinha = cozinhaService.salvar(novaCozinha);
//	}
//	
//    @Test(expected = EntidadeEmUsoException.class)
//    public void deveFalhar_QuandoExcluirCozinhaEmUso() {
//        cozinhaService.excluir(1L);
//    }

}
